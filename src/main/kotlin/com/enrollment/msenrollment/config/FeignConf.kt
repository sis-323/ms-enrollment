package com.enrollment.msenrollment.config

import feign.codec.Encoder
import feign.form.spring.SpringFormEncoder
import feign.codec.Encoder.Default

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.beans.factory.ObjectFactory
import org.springframework.beans.BeansException
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.SpringEncoder

@Configuration
class FeignSupportConfig {

    @Bean
    fun multipartFormEncoder(): Encoder {
        return SpringFormEncoder(SpringEncoder(object : ObjectFactory<HttpMessageConverters> {
            @Throws(BeansException::class)
            override fun getObject(): HttpMessageConverters {
                return HttpMessageConverters(RestTemplate().messageConverters)
            }
        }))
    }
}
