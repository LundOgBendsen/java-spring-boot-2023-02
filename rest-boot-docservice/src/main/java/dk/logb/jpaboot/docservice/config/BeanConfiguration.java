package dk.logb.jpaboot.docservice.config;

import dk.logb.jpaboot.docservice.document.DocumentService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;

@Configuration
public class BeanConfiguration {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DocumentService getDocumentService() {
        return new DocumentService();
    }

   /*
    @Bean
    public HttpMessageConverter<Object> createXmlHttpMessageConverter() {
        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        return xmlConverter;
    }
    */

}
