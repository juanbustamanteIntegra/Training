package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
* Provide JPA operations running inside of a thread pool sized to the connection pool
*/
public class JPAUserRepository implements UserRepository {

  private final JPAApi jpaApi;
  private final DatabaseExecutionContext executionContext;

  @Inject
  public JPAUserRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
    this.jpaApi = jpaApi;
    this.executionContext = executionContext;
  }

  @Override
  public CompletionStage<User> add(User user) {
    return supplyAsync(() -> wrap(em -> insert(em, user)), executionContext);
  }

  @Override
  public CompletionStage<Stream<User>> findOne(String user) {
    return supplyAsync(() -> wrap(em -> findOne(em, user)), executionContext);
  }

  private <T> T wrap(Function<EntityManager, T> function) {
    return jpaApi.withTransaction(function);
  }

  private User insert(EntityManager em, User user) {
    List<User> persons = em.createQuery("select u from User u where u.email = '"+ user.getEmail() +"'").getResultList();
    if(persons.size()==0)
    {
      em.persist(user);
      return user;
    }
    else
    {
      return null;
    }
  }

  private Stream<User> findOne(EntityManager em, String user) {
    List<User> persons = em.createQuery("select u from User u where u.email = '"+ user +"'").getResultList();
    return persons.stream();
  }
}
