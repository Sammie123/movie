import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/studios", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("studios", Studio.all());
      model.put("template", "templates/studios.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/studios/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Studio studio = Studio.find(Integer.parseInt(request.params(":id")));
      model.put("studio", studio);
      model.put("template", "templates/studio.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/studios-new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/studio-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/studios", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Studio newStudio = new Studio(name);
      newStudio.save();
      model.put("template", "templates/studio-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/studios/:studio_id/movies/:movie_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Studio studio = Studio.find(Integer.parseInt(request.params(":studio_id")));
      Movie movie = Movie.find(Integer.parseInt(request.params(":movie_id")));
      model.put("movie", movie);
      model.put("template", "templates/movie.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("studios/:id/movies-new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Studio studio = Studio.find(Integer.parseInt(request.params(":id")));
      model.put("studio", studio);
      model.put("template", "templates/studio-movies-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/movies", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Studio studio = Studio.find(Integer.parseInt(request.queryParams("studioId")));
      String name = request.queryParams("name");
      Movie newMovie = new Movie(name, studio.getId());
      newMovie.save();
      model.put("studio", studio);
      model.put("template", "templates/studio-movie-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
