import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NameCardsDAO {
  // private static Connection con ;
  // private PreparedStatement pstmt;
  // private ResultSet rs;

  public static Connection set_connection(){
    try {
      Class.forName("org.h2.Driver");
      Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/~/business_card", "sa", "");
      return con;
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("JDBCドライバのロードに失敗しました");
    } catch (SQLException e) {
      throw new IllegalStateException("データベースへの接続に失敗しました");
    }
  }

  // public void close() {
  //   try {
  //     if (rs != null) {
  //       rs.close();
  //     }
  //     if (pstmt != null) {
  //       pstmt.close();
  //     }
  //   } catch (SQLException e) {
  //     throw new IllegalStateException("データベースの接続を閉じる際にエラーが発生しました");
  //   }
  // }

  public static NameCard select(int id) throws SQLException {
    Connection con = set_connection();
    try {
      String sql = "SELECT * FROM NAMECARDS WHERE ID = ?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        NameCard name_card = get_name_card(rs);
        return name_card;
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new IllegalStateException("データベースへの接続に失敗しました");
    }
  }

  public static List<NameCard> selectAll() throws SQLException {

    try {
      Connection con = set_connection();
      
      String sql = "SELECT * FROM NAMECARDS";
      PreparedStatement pstmt = con.prepareStatement(sql);
      
      ResultSet rs = pstmt.executeQuery();
      List<NameCard> name_cards = get_name_cards(rs);

      pstmt.close();

      return name_cards;

    } catch (SQLException e) {
      throw new IllegalStateException("データベースへの接続に失敗しました");
    }

  }

  public static void delete(int id) throws SQLException {
    try {
      Connection con = set_connection();
      
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
      Connection con = set_connection();
      
      String sql = "INSERT INTO NAMECARDS(person_lname, person_fname, person_title, person_email, person_tel, dept_name, office_zip, office_address, office_tel, office_fax, company_name, company_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement pstmt = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

      pstmt = get_pstmt(name_card, pstmt);
      int result = pstmt.executeUpdate();

      if (result == 1) {
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          int id = rs.getInt(1);
          System.out.println("管理番号" + id + " として登録しました\n");
        }
      } else {
        System.out.println("登録できませんでした\n");
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void update(NameCard name_card){
    try {
      Connection con = set_connection();
      
      String sql = "UPDATE NAMECARDS SET person_lname = ?, person_fname = ?, person_title = ?, person_email = ?, person_tel = ?, dept_name = ?, office_zip = ?, office_address = ?, office_tel = ?, office_fax = ?, company_name = ?, company_url = ? WHERE id = ?";
      PreparedStatement pstmt = con.prepareStatement(sql);

      pstmt = get_pstmt(name_card, pstmt);
      pstmt.setInt(13, name_card.getId());
      int result = pstmt.executeUpdate();

      if (result == 1) {
        System.out.println("更新しました\n");
      } else {
        System.out.println("更新できませんでした\n");
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static List<NameCard> search_for(String word) throws SQLException {
    List<NameCard> name_cards = new ArrayList<NameCard>();
    try {
      Connection con = set_connection();
      
      String sql = "SELECT * FROM NAMECARDS WHERE person_lname LIKE ? OR person_fname LIKE ? OR company_name LIKE ?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, "%" + word + "%");
      pstmt.setString(2, "%" + word + "%");
      pstmt.setString(3, "%" + word + "%");
      ResultSet rs = pstmt.executeQuery();

      name_cards = get_name_cards(rs);

      pstmt.close();
      return name_cards;

    } catch (SQLException e) {
      e.printStackTrace();
      return name_cards;
    }
  }

  public static NameCard get_name_card(ResultSet rs) {
    NameCard name_card = new NameCard();
    try {
      name_card.setId(rs.getInt("id"));
      name_card.setPersonLname(rs.getString("person_lname"));
      name_card.setPersonFname(rs.getString("person_fname"));
      name_card.setPersonTitle(rs.getString("person_title"));
      name_card.setPersonEmail(rs.getString("person_email"));
      name_card.setPersonTel(rs.getString("person_tel"));
      name_card.setDeptName(rs.getString("dept_name"));
      name_card.setOfficeZip(rs.getString("office_zip"));
      name_card.setOfficeAddress(rs.getString("office_address"));
      name_card.setOfficeTel(rs.getString("office_tel"));
      name_card.setOfficeFax(rs.getString("office_fax"));
      name_card.setCompanyName(rs.getString("company_name"));
      name_card.setCompanyUrl(rs.getString("company_url"));
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return name_card;


  }

  public static List<NameCard> get_name_cards(ResultSet rs) {
    List<NameCard> name_cards = new ArrayList<NameCard>();

    try {
      while (rs.next()) {
        NameCard name_card = new NameCard();

        name_card.setId(rs.getInt("id"));
        name_card.setPersonLname(rs.getString("person_lname"));
        name_card.setPersonFname(rs.getString("person_fname"));
        name_card.setPersonTitle(rs.getString("person_title"));
        name_card.setPersonEmail(rs.getString("person_email"));
        name_card.setPersonTel(rs.getString("person_tel"));
        name_card.setDeptName(rs.getString("dept_name"));
        name_card.setOfficeZip(rs.getString("office_zip"));
        name_card.setOfficeAddress(rs.getString("office_address"));
        name_card.setOfficeTel(rs.getString("office_tel"));
        name_card.setOfficeFax(rs.getString("office_fax"));
        name_card.setCompanyName(rs.getString("company_name"));
        name_card.setCompanyUrl(rs.getString("company_url"));

        name_cards.add(name_card);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return name_cards;

  }

  public static PreparedStatement get_pstmt(NameCard name_card, PreparedStatement pstmt) {
    try {
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
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return pstmt;
  }

}
