package com.HLS_user_service.service

import com.HLS_user_service.dto.request.UpdateVideoStatus
import com.HLS_user_service.entity.VideoDetails
import com.HLS_user_service.repository.VideoDetailsRepository
import com.HLS_user_service.util.exception.BadRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.sts.StsClient
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest
import java.util.UUID

@Service
class VideoDetailsService(
    private val videoDetailsRepository: VideoDetailsRepository,
    private val tokenDetailsService: TokenDetailsService,
    @Value("\${aws.s3.bucket-name}") private val bucketName: String,
    @Value("\${aws.s3.role}") private val role: String,
    private val s3Client: S3Client,
    private val stsClient: StsClient
) {
    companion object {
        private val log = LoggerFactory.getLogger(VideoDetailsRepository::class.java)
        private const val ONE_HOUR = 3600
    }

    fun createUploadSessionForCLI(token: String): SessionResponse {
        val tokenDetails = tokenDetailsService.getTokenDetails(token)
        if(!tokenDetailsService.isTokenValid(tokenDetails)) {
            throw BadRequest("Invalid token")
        }

        val uuidPath = UUID.randomUUID().toString()

        val policy = buildPolicy(uuidPath)

        val request = AssumeRoleRequest.builder()
            .roleArn(role)
            .roleSessionName("upload-session-$uuidPath")
            .durationSeconds(ONE_HOUR)
            .policy(policy)
            .build()

        val response = stsClient.assumeRole(request)

        val credentials = response.credentials()

        videoDetailsRepository.save(VideoDetails(path = uuidPath, user = tokenDetails.user))

        return SessionResponse(
            path = uuidPath,
            bucket = bucketName,
            credentials = TemporaryCredentials(
                accessKey = credentials.accessKeyId(),
                secretKey = credentials.secretAccessKey(),
                sessionToken = credentials.sessionToken(),
                expiresIn = ONE_HOUR
            )
        )
    }

    fun updateVideoDetailsForCLI(updateVideoStatus: UpdateVideoStatus) {
        val tokenDetails = tokenDetailsService.getTokenDetails(updateVideoStatus.token)
        if (!tokenDetailsService.isTokenValid(tokenDetails)) {
            throw BadRequest("Invalid token")
        }

        val videoDetails = videoDetailsRepository.findByPath(updateVideoStatus.path)
            ?: throw BadRequest("Video details not found for path: ${updateVideoStatus.path}")

        if (updateVideoStatus.isValid) {
            videoDetails.isValid = true
            videoDetailsRepository.save(videoDetails)
        } else {
            val objects = s3Client.listObjectsV2 {
                it.bucket(bucketName)
                it.prefix(videoDetails.path)
            }

            objects.contents().forEach { obj ->
                s3Client.deleteObject { req ->
                    req.bucket(bucketName)
                    req.key(obj.key())
                }
            }
            videoDetailsRepository.delete(videoDetails)
        }
    }



    private fun buildPolicy(prefix: String): String {
        return """
        {
          "Version": "2012-10-17",
          "Statement": [
            {
              "Effect": "Allow",
              "Action": [
                "s3:PutObject"
              ],
              "Resource": "arn:aws:s3:::$bucketName/$prefix*"
            }
          ]
        }
        """.trimIndent()
    }
}