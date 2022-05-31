import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NameCardsDAO {
  private Connection con;
  private PreparedStatement pstmt;
  private ResultSet rs;

  public NameCardsDAO() {
    try {
      Class.forName("org.h2.Driver");
      con = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/~/business_card", "sa", "");
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("JDBCドライバのロードに失敗しました");
    } catch (SQLException e) {
      throw new IllegalStateException("データベースへの接続に失敗しました");
    }
  }

  public void close() {
    try {
      if (rs != null) {
        rs.close();
      }
      if (pstmt != null) {
        pstmt.close();
      }
    } catch (SQLException e) {
      throw new IllegalStateException("データベースの接続を閉じる際にエラーが発生しました");
    }
  }

  public static NameCard select(int id) throws SQLException {
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("JDBCドライバのロードに失敗しました");
    }
    Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/~/business_card", "sa", "");
    try {
      String sql = "SELECT * FROM NAMECARDS WHERE ID = ?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        NameCard nameCard = new NameCard();
        nameCard.setId(rs.getInt("id"));
        nameCard.setPersonLname(rs.getString("person_lname"));
        nameCard.setPersonFname(rs.getString("person_fname"));
        nameCard.setPersonTitle(rs.getString("person_title"));
        nameCard.setPersonEmail(rs.getString("person_email"));
        nameCard.setPersonTel(rs.getString("person_tel"));
        nameCard.setDeptName(rs.getString("dept_name"));
        nameCard.setOfficeZip(rs.getString("office_zip"));
        nameCard.setOfficeAddress(rs.getString("office_address"));
        nameCard.setOfficeTel(rs.getString("office_tel"));
        nameCard.setOfficeFax(rs.getString("office_fax"));
        nameCard.setCompanyUrl(rs.getString("company_url"));

        return nameCard;
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new IllegalStateException("データベースへの接続に失敗しました");
    }
  }

  public static List<NameCard> selectAll() throws SQLException {
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("JDBCドライバのロードに失敗しました");
    }
    
    Connection con = null;
    try {
      con = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/~/business_card", "sa", "");
      
      String sql = "SELECT * FROM NAMECARDS";
      PreparedStatement pstmt = con.prepareStatement(sql);
      
      ResultSet rs = pstmt.executeQuery();

      List<NameCard> name_cards = new ArrayList<>();
      while (rs.next()) {
        NameCard nameCard = new NameCard();
        nameCard.setId(rs.getInt("id"));
        nameCard.setPersonLname(rs.getString("person_lname"));
        nameCard.setPersonFname(rs.getString("person_fname"));
        nameCard.setPersonTitle(rs.getString("person_title"));
        nameCard.setPersonEmail(rs.getString("person_email"));
        nameCard.setPersonTel(rs.getString("person_tel"));
        nameCard.setDeptName(rs.getString("dept_name"));
        nameCard.setOfficeZip(rs.getString("office_zip"));
        nameCard.setOfficeAddress(rs.getString("office_address"));
        nameCard.setOfficeTel(rs.getString("office_tel"));
        nameCard.setOfficeFax(rs.getString("office_fax"));
        nameCard.setCompanyName(rs.getString("company_name"));
        nameCard.setCompanyUrl(rs.getString("company_url"));

        name_cards.add(nameCard);
      }
      pstmt.close();

      return name_cards;

    } catch (SQLException e) {
      throw new IllegalStateException("データベースへの接続に失敗しました");
    }

  }

  public void insert(String person_lname, String person_fname, String person_title, String person_email, String person_tel, String dept_name, String office_zip, String office_address, String office_tel, String office_fax, String company_name, String company_url) {
    try {
      String sql = "INSERT INTO NAMECARDS (person_lname, person_fname, person_title, person_email, person_tel, dept_name, office_zip, office_address, office_tel, office_fax, company_name, company_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, person_lname);
      pstmt.setString(2, person_fname);
      pstmt.setString(3, person_title);
      pstmt.setString(4, person_email);
      pstmt.setString(5, person_tel);
      pstmt.setString(6, dept_name);
      pstmt.setString(7, office_zip);
      pstmt.setString(8, office_address);
      pstmt.setString(9, office_tel);
      pstmt.setString(10, office_fax);
      pstmt.setString(11, company_name);
      pstmt.setString(12, company_url);
      pstmt.executeUpdate();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void delete(int id) throws SQLException {
    try {
      Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/~/business_card", "sa", "");
      
      String sql = "DELETE FROM NAMECARDS WHERE ID = ?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, id);
      int result = pstmt.executeUpdate();
      if (result == 1) {
        System.out.println("削除しました");
      } else {
        System.out.println("削除できませんでした");
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void insert(NameCard name_card) throws SQLException {
    try {
      Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/~/business_card", "sa", "");
      
      String sql = "INSERT INTO NAMECARDS(person_lname, person_fname, person_title, person_email, person_tel, dept_name, office_zip, office_address, office_tel, office_fax, company_name, company_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement pstmt = con.prepareStatement(sql);

      pstmt.setString(1, name_card.getPersonLname());
      pstmt.setString(2, name_card.getPersonFname());
      pstmt.setString(3, name_card.getPersonTitle());
      pstmt.setString(4, name_card.getPersonEmail());
      pstmt.setString(5, name_card.getPersonTel());
      pstmt.setString(6, name_card.getDeptName());
      pstmt.setString(7, name_card.getOfficeZip());
      pstmt.setString(8, name_card.getOfficeAddress());
      pstmt.setString(9, name_card.getOfficeTel());
      pstmt.setString(10, name_card.getOfficeFax());
      pstmt.setString(11, name_card.getCompanyName());
      pstmt.setString(12, name_card.getCompanyUrl());
      System.out.println(pstmt);
      int result = pstmt.executeUpdate();

      if (result == 1) {
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          int id = rs.getInt(1);
          System.out.println("管理番号" + id + " として登録しました");
        }
      } else {
        System.out.println("登録できませんでした");
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }



}
