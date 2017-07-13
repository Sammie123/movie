import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.util.Arrays;


public class Studio {
  private String name;
  private int id;
  // private static List<Studio> instances = new ArrayList<Studio>();
  // private List<Movie> movie;

  public Studio(String name) {
    this.name = name;
    // instances.add(this);
  }


  public String getName() {
      return name;
  }

  public static List<Studio> all() {
    String sql = "SELECT id, name FROM studios";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Studio.class);
    }
  }

  public int getId() {
    return id;
  }

  public List<Movie> getMovies() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM movies WHERE studioId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Movie.class);
    }
  }

  public void addMovie() {

  }

  public static Studio find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM studios WHERE id=:id";
      Studio studio = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Studio.class);
      return studio;
    }
  }

  @Override
  public boolean equals(Object otherStudio) {
    if (!(otherStudio instanceof Studio)) {
      return false;
    } else {
      Studio newStudio = (Studio) otherStudio;
      return this.getName().equals(newStudio.getName()) && this.getId() == newStudio.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO studios (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }
}
