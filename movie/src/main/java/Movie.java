import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.util.Arrays;

public class Movie {
  private String name;
  private int id;
  private int studioId;

  public Movie (String movie, int studioId) {
    this.name = name;
    this.studioId = studioId;
  }

  public String getName() {
    return name;
  }

  public int getStudioId() {
    return studioId;
  }

  public int getId() {
    return id;
  }

  public static List<Movie> all() {
    String sql = "SELECT id, name, studioId FROM movies";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Movie.class);
    }
  }

  @Override
  public boolean equals(Object otherMovie) {
    if(!(otherMovie instanceof Movie)) {
      return false;
    } else {
      Movie newMovie = (Movie) otherMovie;
      return this.getName().equals(newMovie.getName()) && this.getId() == newMovie.getId() && this.getStudioId() == newMovie.getStudioId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO movies (name, studioId) VALUES (:name, :studioId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("studioId", this.studioId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Movie find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM movies where id=:id";
      Movie movie = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Movie.class);
      return movie;
    }
  }
}
