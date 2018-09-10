package com.lucky5.bookmarker;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookmarkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmarkerApplication.class, args);
	}

	@Bean
	@ConditionalOnMissingBean
	CollectorRegistry metricRegistry() {
		return CollectorRegistry.defaultRegistry;
	}

	@Bean
	ServletRegistrationBean registerPrometheusExporterServlet(CollectorRegistry metricRegistry) {
		return new ServletRegistrationBean(new MetricsServlet(metricRegistry), "/prometheus");
	}
}
