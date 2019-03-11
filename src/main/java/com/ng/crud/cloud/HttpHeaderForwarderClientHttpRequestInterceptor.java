package com.ng.crud.cloud;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Collections;

// use:
// restTemplate.setInterceptors(Collections.singletonList(new HttpHeaderForwarderClientHttpRequestInterceptor()));

public class HttpHeaderForwarderClientHttpRequestInterceptor implements ClientHttpRequestInterceptor
{
	@Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
	{
        request.getHeaders().putAll(HttpHeaderForwarderHandlerInterceptor.getHeaders());
        return execution.execute(request, body);
    }
}
