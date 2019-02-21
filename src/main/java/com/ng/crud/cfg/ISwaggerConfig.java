package com.ng.crud.cfg;

import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.Tag;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import javax.ws.rs.ext.Provider;

@SwaggerDefinition(
        info = @Info(
                description = "Gets the Events",
                version = "V12.0.13",
                title = "The Events API",
                termsOfService = "http://me.io/terms.html",
                contact = @Contact(
                   name = "me", 
                   email = "me@me.io", 
                   url = "http://me.io"
                ),
                license = @License(
                   name = "Apache 2.0", 
                   url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json"},
        produces = {"application/json"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        tags = {
                @Tag(name = "Events", description = "Events Tag")
        }, 
        externalDocs = @ExternalDocs(value = "Me", url = "http://me.io/me.html")
)
@Provider
public interface ISwaggerConfig
{

}
