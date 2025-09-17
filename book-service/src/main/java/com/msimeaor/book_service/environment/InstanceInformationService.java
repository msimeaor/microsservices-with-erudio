package com.msimeaor.book_service.environment;


import lombok.Getter;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Getter
@Service
public class InstanceInformationService implements ApplicationListener<WebServerInitializedEvent> {

  private String port;

  @Override
  public void onApplicationEvent(WebServerInitializedEvent event) {
    this.port = String.valueOf(event.getWebServer().getPort());
  }

}
