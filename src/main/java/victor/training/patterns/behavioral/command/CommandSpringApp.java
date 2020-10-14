// Generated by delombok at Wed Oct 14 12:32:37 EEST 2020
package victor.training.patterns.behavioral.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import static java.util.Arrays.asList;
import static victor.training.patterns.stuff.ThreadUtils.sleepq;

@EnableAsync
@SpringBootApplication
@EnableBinding({Sink.class, Source.class})
public class CommandSpringApp {
   public static void main(String[] args) {
      SpringApplication.run(CommandSpringApp.class, args).close(); // Note: .close to stop executors after CLRunner finishes
   }

   @Bean
   public ThreadPoolTaskExecutor executor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(1);
      executor.setMaxPoolSize(1);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("barman-");
      executor.initialize();
      executor.setWaitForTasksToCompleteOnShutdown(true);
      return executor;
   }
}

@Component
class Drinker implements CommandLineRunner {
   @java.lang.SuppressWarnings("all")
   private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Drinker.class);
   @Autowired
   private Barman barman;
   @Autowired
   private ServiceActivatorPattern serviceActivatorPattern;

   // TODO [1] inject and use a ThreadPoolTaskExecutor.submit
   // TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
   // TODO [3] wanna try it out over JMS? try out ServiceActivatorPattern
   public void run(String... args) {
      log.debug("Submitting my order");
      long t0 = System.currentTimeMillis();
      log.debug("Waiting for my drinks...");
      Beer beer = barman.pourBeer();
      Vodka vodka = barman.pourVodka();
      long t1 = System.currentTimeMillis();
      log.debug("Got my order in {} ms ! Enjoying {}", t1 - t0, asList(beer, vodka));
   }
}

@Service
class Barman {
   @java.lang.SuppressWarnings("all")
   private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Barman.class);

   public Beer pourBeer() {
      log.debug("Pouring Beer...");
      sleepq(1000);
      return new Beer();
   }

   public Vodka pourVodka() {
      log.debug("Pouring Vodka...");
      sleepq(1000);
      return new Vodka();
   }
}

class Beer {
   @java.lang.SuppressWarnings("all")
   public Beer() {
   }

   @java.lang.Override
   @java.lang.SuppressWarnings("all")
   public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Beer)) return false;
      final Beer other = (Beer) o;
      if (!other.canEqual((java.lang.Object) this)) return false;
      return true;
   }

   @java.lang.SuppressWarnings("all")
   protected boolean canEqual(final java.lang.Object other) {
      return other instanceof Beer;
   }

   @java.lang.Override
   @java.lang.SuppressWarnings("all")
   public int hashCode() {
      final int result = 1;
      return result;
   }

   @java.lang.Override
   @java.lang.SuppressWarnings("all")
   public java.lang.String toString() {
      return "Beer()";
   }
}

class Vodka {
   @java.lang.SuppressWarnings("all")
   public Vodka() {
   }

   @java.lang.Override
   @java.lang.SuppressWarnings("all")
   public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Vodka)) return false;
      final Vodka other = (Vodka) o;
      if (!other.canEqual((java.lang.Object) this)) return false;
      return true;
   }

   @java.lang.SuppressWarnings("all")
   protected boolean canEqual(final java.lang.Object other) {
      return other instanceof Vodka;
   }

   @java.lang.Override
   @java.lang.SuppressWarnings("all")
   public int hashCode() {
      final int result = 1;
      return result;
   }

   @java.lang.Override
   @java.lang.SuppressWarnings("all")
   public java.lang.String toString() {
      return "Vodka()";
   }
}
