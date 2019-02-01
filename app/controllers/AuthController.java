package controllers;

import models.Person;
import models.PersonRepository;
import models.User;
import models.UserRepository;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.stream.Stream;
import java.util.List;

import play.api.Logger;
import services.SESEmail;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

import static play.libs.Json.toJson;


/**
* The controller keeps all database operations behind the repository, and uses
* {@link play.libs.concurrent.HttpExecutionContext} to provide access to the
* {@link play.mvc.Http.Context} methods like {@code request()} and {@code flash()}.
*/
public class AuthController extends Controller {

  private final FormFactory formFactory;
  private final UserRepository userRepository;
  private final HttpExecutionContext ec;

  @Inject
  public AuthController(FormFactory formFactory, UserRepository userRepository, HttpExecutionContext ec) {
    this.formFactory = formFactory;
    this.userRepository = userRepository;
    this.ec = ec;
  }

  public Result index() {
    return ok(views.html.index.render());
  }

  public Result renderLogin() {
    return ok(views.html.login.render());
  }

  public Result test2() {
    return ok(views.html.login2.render());
  }

  public CompletionStage<Result> createUser() {
    User user = formFactory.form(User.class).bindFromRequest().get();
    return userRepository.add(user).thenApplyAsync(p -> {
      if(p != null)
      {
        session("connected", user.getEmail());
        SESEmail.sendEmail(false, user.getEmail());
        return redirect(routes.PersonController.index());
      }
      else
      {
        return redirect(routes.AuthController.renderLogin());
      }
    }, ec.current());
  }

  public CompletionStage<Result> login() {
    User user = formFactory.form(User.class).bindFromRequest().get();
    return userRepository.findOne(user.getEmail()).thenApplyAsync(p -> {
      List<User> logged = p.collect(Collectors.toList());
      if(logged.size() != 0 &&  (logged.get(0).getPassword().equals(user.getPassword())) )
      {
        session("connected", logged.get(0).getEmail());
        return redirect(routes.PersonController.index());
      }
      else
      {
        return redirect(routes.AuthController.test2());
      }
    }, ec.current());
  }

  public Result logout() {
    String user = session("connected");
    session().remove("connected");
    SESEmail.sendEmail(true, user);
    return redirect(routes.AuthController.renderLogin());
  }

}
