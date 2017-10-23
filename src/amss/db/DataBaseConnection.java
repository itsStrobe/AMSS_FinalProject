package amss.db;

import amss.app.Individuos.*;
import amss.app.Elementos.*;
import amss.app.util.*;
import amss.app.util.Time;

import java.sql.*;
import java.util.*;

public final class DataBaseConnection {

  private Connection c = null;

  public DataBaseConnection() {
    c = null;
  }

  private void open() {
    if (c != null) {
      System.out.println("ERROR: already connected to the Database");
    } else {
      try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:./bin/amss/db/Database.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");
      } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        System.exit(0);
      }
    }
  }

  private void close() {
    try {
      c.close();
      c = null;
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public boolean dbUpdate(Vector<String> parameters, String str) {
    open();
    boolean status = true;
    int parCounter = 1;

    try {
      PreparedStatement stmt = c.prepareStatement(str);

      for(String parameter : parameters) {
        stmt.setString(parCounter, parameter);
        parCounter++;
      }

      stmt.executeUpdate();
      stmt.close();
      c.commit();
    } catch (Exception e) {
      System.out.println("Error adding element to database");
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      status = false;
      System.exit(0);
    }
    close();
    return status;
  }

  // Recupera los inquilinos en base a una query a la tabla INQUILINOS
  public Collection<Inquilino> getInquilinos(Vector<String> parameters, String str) {

    final Collection<Inquilino> found = new ArrayList<>();
    int iParCounter = 1;

    open();
    try {

      PreparedStatement stmt = c.prepareStatement(str);

      for(String parameter : parameters) {
        stmt.setString(iParCounter, parameter);
        iParCounter++;
      }

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Uuid inquilinoID = Uuid.parse(rs.getString("ID"));
        String inquilinoNombre = rs.getString("NOMBRE");
        String direccion = rs.getString("DIRECCION");
        Time fechaNacimiento = Time.fromMs(rs.getLong("FECHANACIMIENTO"));
        int edad = rs.getInt("EDAD");
        Uuid responsableID = Uuid.parse(rs.getString("RESPONSABLE"));
        char estatus = rs.getString("ESTATUS").charAt(0);
        String cuarto = rs.getString("CUARTO");

        Inquilino inquilino = new Inquilino(inquilinoID, inquilinoNombre, direccion, edad, fechaNacimiento, responsableID, estatus, cuarto);
        found.add(inquilino);
      }

      rs.close();
      stmt.close();

    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    close();
    return found;
  }

  // Recupera los Familiares de un Inquilino en base a una query del Join de INQUILINO_FAMILIAR
  // y FAMILIARES
  public Collection<Familiar> getFamiliares(Vector<String> parameters, String str) {

    final Collection<Familiar> found = new ArrayList<>();
    int iParCounter = 1;

    open();
    try {

      PreparedStatement stmt = c.prepareStatement(str);

      for(String parameter : parameters) {
        stmt.setString(iParCounter, parameter);
        iParCounter++;
      }

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Uuid familiarID = Uuid.parse(rs.getString("ID"));
        Uuid inquilinoID = Uuid.parse(rs.getString("INQUILINOID"));
        String nombre = rs.getString("NOMBRE");
        String telefono = rs.getString("TELEFONO");
        String direccion = rs.getString("DIRECCION");

        Familiar familiar = new Familiar(familiarID, inquilinoID, nombre, telefono, direccion);
        found.add(familiar);
      }

      rs.close();
      stmt.close();

    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    close();
    return found;
  }

  // Recupera las recetas en base a una query a la tabla de RECETAS
  public Collection<Receta> getRecetas(Vector<String> parameters, String str) {

    final Collection<Receta> found = new ArrayList<>();
    int iParCounter = 1;

    open();
    try {

      PreparedStatement stmt = c.prepareStatement(str);

      for(String parameter : parameters) {
        stmt.setString(iParCounter, parameter);
        iParCounter++;
      }

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Uuid id = Uuid.parse(rs.getString("ID"));
        String strDocNombre = rs.getString("DOCTOR");
        Uuid pacienteID = Uuid.parse(rs.getString("PACIENTE"));
        Time tFecha = Time.fromMs(rs.getLong("FECHA"));

        Receta receta = new Receta(id, strDocNombre, pacienteID, tFecha);
        found.add(receta);
      }

      rs.close();
      stmt.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    close();
    return found;
  }

  // Recupera las Medicinas en base a una query de la union de las tablas de RECETA_MEDICINA y MEDICINAS
  public Collection<RecetaMedicina> getMedicinasOfReceta(Vector<String> parameters, String str) {

    final Collection<RecetaMedicina> found = new ArrayList<>();
    int iParCounter = 1;

    open();
    try {

      PreparedStatement stmt = c.prepareStatement(str);

      for(String parameter : parameters) {
        stmt.setString(iParCounter, parameter);
        iParCounter++;
      }

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Uuid medicinaID = Uuid.parse(rs.getString("MEDICINA"));
        Uuid recetaId = Uuid.parse(rs.getString("RECETA"));
        boolean morning = (rs.getInt("MANANA") == 1);
        boolean evening = (rs.getInt("TARDE") == 1);
        boolean night = (rs.getInt("NOCHE") == 1);
        Time tFechaFin = Time.fromMs(rs.getLong("FECHAFIN"));

        RecetaMedicina recetaMedicina = new RecetaMedicina(medicinaID, recetaId, morning, evening, night, tFechaFin);
        found.add(recetaMedicina);
      }

      rs.close();
      stmt.close();

    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    close();
    return found;
  }

  // Recupera las medicinas en base a una query de la tabla de MEDICINAS
  public Collection<Medicina> getMedicinas(Vector<String> parameters, String str) {

    final Collection<Medicina> found = new ArrayList<>();
    int iParCounter = 1;

    open();
    try {

      PreparedStatement stmt = c.prepareStatement(str);

      for(String parameter : parameters) {
        stmt.setString(iParCounter, parameter);
        iParCounter++;
      }

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Uuid medicinaID = Uuid.parse(rs.getString("ID"));
        String medicinaNom = rs.getString("NOMBRE");

        Medicina medicina = new Medicina(medicinaID, medicinaNom);
        found.add(medicina);
      }

      rs.close();
      stmt.close();

    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    close();
    return found;
  }

  // Recupera las medicinas del paciente en base a una query de la union de las tablas de MEDICINA_PACIENTE
  // y MEDICINA
  public Collection<PacienteMedicina> getMedicinasOfPaciente(Vector<String> parameters, String str) {

    final Collection<PacienteMedicina> found = new ArrayList<>();
    int iParCounter = 1;

    open();
    try {

      PreparedStatement stmt = c.prepareStatement(str);

      for(String parameter : parameters) {
        stmt.setString(iParCounter, parameter);
        iParCounter++;
      }

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Uuid pacienteID = Uuid.parse(rs.getString("PACIENTE"));
        Uuid medicinaID = Uuid.parse(rs.getString("MEDICINA"));
        int cantidad = rs.getInt("CANTIDAD");

        PacienteMedicina pacienteMedicina = new PacienteMedicina(medicinaID, pacienteID, cantidad);
        found.add(pacienteMedicina);
      }

      rs.close();
      stmt.close();

    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    close();
    return found;
  }

  public Connection getConnection() {
    return c;
  }

  public void createTables() {
    c = null;
    PreparedStatement stmt = null;
    open();

    try {
      String sql = "CREATE TABLE FAMILIARES " +
          "(ID             VARCHAR(16) PRIMARY KEY NOT NULL, " +
          " INQUILINOID    VARCHAR(16)             NOT NULL, " +
          " NOMBRE         CHAR(50)                NOT NULL, " +
          " TELEFONO       CHAR(14)                NOT NULL, " +
          " DIRECCION      TEXT                    NOT NULL,  " +
          " FOREIGN KEY(INQUILINOID) REFERENCES INQUILINOS(ID))";
      stmt = c.prepareStatement(sql);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Table <FAMILIARES> created successfully");

    try {
      String sql = "CREATE TABLE INQUILINOS " +
          "(ID              VARCHAR(16) PRIMARY KEY NOT NULL," +
          " NOMBRE          CHAR(50)                NOT NULL, " +
          " DIRECCION       TEXT                    NOT NULL, " +
          " FECHANACIMIENTO BIGINT                  NOT NULL, " +
          " EDAD            INTEGER                 NOT NULL, " +
          " RESPONSABLE     VARCHAR(16)             NOT NULL, " +
          " ESTATUS         CHAR(1)                 NOT NULL, " +
          " CUARTO          CHAR(10)                NOT NULL, " +
          " FOREIGN KEY(RESPONSABLE) REFERENCES FAMILIARES(ID))";
      stmt = c.prepareStatement(sql);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Table <INQUILINOS> created successfully");

    try {
      String sql = "CREATE TABLE RECETAS " +
          "(ID        VARCHAR(16) PRIMARY KEY NOT NULL, " +
          " DOCTOR    CHAR(50)                NOT NULL, " +
          " PACIENTE  VARCHAR(16)             NOT NULL, " +
          " FECHA     BIGINT                  NOT NULL, " +
          " FOREIGN KEY(PACIENTE) REFERENCES INQUILINOS(ID))";
      stmt = c.prepareStatement(sql);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Table <RECETAS> created successfully");

    try {
      String sql = "CREATE TABLE MEDICINAS " +
          "(ID        VARCHAR(16) PRIMARY KEY NOT NULL, " +
          " NOMBRE    CHAR(50)                NOT NULL)";
      stmt = c.prepareStatement(sql);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Table <MEDICINAS> created successfully");


    try {
      String sql = "CREATE TABLE RECETA_MEDICINA " +
          "(ID          VARCHAR(32) PRIMARY KEY NOT NULL, " +
          " RECETA      VARCHAR(16)             NOT NULL, " +
          " MEDICINA    VARCHAR(16)             NOT NULL, " +
          " MANANA      INTEGER                 DEFAULT 0, " +
          " TARDE       INTEGER                 DEFAULT 0, " +
          " NOCHE       INTEGER                 DEFAULT 0, " +
          " FECHAFIN    BIGINT                  NOT NULL, " +
          " FOREIGN KEY(RECETA)   REFERENCES RECETAS(ID), " +
          " FOREIGN KEY(MEDICINA) REFERENCES MEDICINA(ID))";
      stmt = c.prepareStatement(sql);
      stmt.executeUpdate();
      stmt.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Table <RECETA_MEDICINA> created successfully");

    try {
      String sql = "CREATE TABLE MEDICINA_PACIENTE " +
          "(ID          VARCHAR(32) PRIMARY KEY NOT NULL, " +
          " PACIENTE    VARCHAR(16)             NOT NULL, " +
          " MEDICINA    VARCHAR(16)             NOT NULL, " +
          " CANTIDAD    INTEGER                 DEFAULT 0, " +
          " FOREIGN KEY(PACIENTE) REFERENCES INQUILINOS(ID), " +
          " FOREIGN KEY(MEDICINA) REFERENCES MEDICINAS(ID))";
      stmt = c.prepareStatement(sql);
      stmt.executeUpdate();
      stmt.close();
      c.commit();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Table <MEDICINA_PACIENTE> created successfully");

    /*
    try {
      String sql = "CREATE TABLE INQUILINO_FAMILIAR " +
          "(ID                VARCHAR(16) PRIMARY KEY NOT NULL, " +
          " INQUILINOID       VARCHAR(16)             NOT NULL, " +
          " FAMILIARID        VARCHAR(16)             NOT NULL, " +
          " FOREIGN KEY(INQUILINOID)       REFERENCES INQUILINOS(ID), " +
          " FOREIGN KEY(FAMILIARID)        REFERENCES FAMILIARES(ID))";
      stmt = c.prepareStatement(sql);
      stmt.executeUpdate();
      stmt.close();
      // c.commit();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Table <INQUILINO_FAMILIAR> created successfully");
    */

    close();
  }

  public void dropTables() {
    open();
    Statement stmt = null;
    //drop tables before creating them
    try {
      stmt = c.createStatement();
      String sql = "DROP TABLE FAMILIARES";
      stmt.executeUpdate(sql);
      stmt.close();

      stmt = c.createStatement();
      sql = "DROP TABLE INQUILINOS";
      stmt.executeUpdate(sql);
      stmt.close();

      stmt = c.createStatement();
      sql = "DROP TABLE RECETAS";
      stmt.executeUpdate(sql);
      stmt.close();

      stmt = c.createStatement();
      sql = "DROP TABLE MEDICINAS";
      stmt.executeUpdate(sql);
      stmt.close();

      stmt = c.createStatement();
      sql = "DROP TABLE RECETA_MEDICINA";
      stmt.executeUpdate(sql);
      stmt.close();

      stmt = c.createStatement();
      sql = "DROP TABLE MEDICINA_PACIENTE";
      stmt.executeUpdate(sql);
      stmt.close();

      /*
      stmt = c.createStatement();
      sql = "DROP TABLE INQUILINO_FAMILIAR";
      stmt.executeUpdate(sql);
      stmt.close();
      */

      c.commit();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    close();
  }

}
