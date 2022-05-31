import java.util.Objects;

public class NameCard extends NameCardsDAO {
  private int id;
  private String person_lname;
  private String person_fname;
  private String person_title;
  private String person_email;
  private String person_tel;
  private String dept_name;
  private String office_zip;
  private String office_address;
  private String office_tel;
  private String office_fax;
  private String company_name;
  private String company_url;


  // public NameCard(String person_lname, String person_fname, String person_title, String person_email, String person_tel, String dept_name, String office_zip, String office_address, String office_tel, String office_fax, String company_name, String company_url) {
  //   this.person_lname = person_lname;
  //   this.person_fname = person_fname;
  //   this.person_title = person_title;
  //   this.person_email = person_email;
  //   this.person_tel = person_tel;
  //   this.dept_name = dept_name;
  //   this.office_zip = office_zip;
  //   this.office_address = office_address;
  //   this.office_tel = office_tel;
  //   this.office_fax = office_fax;
  //   this.company_name = company_name;
  //   this.company_url = company_url;
  // }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPersonLname() {
    return person_lname;
  }

  public void setPersonLname(String _person_lname) {
    this.person_lname = common_check(this.person_lname, _person_lname);
  }

  public String getPersonFname() {
    return person_fname;
  }

  public void setPersonFname(String _person_fname) {
    this.person_fname = common_check(this.person_fname, _person_fname);
  }

  public String getPersonTitle() {
    return person_title;
  }

  public void setPersonTitle(String _person_title) {
    person_title = common_check(this.person_title, _person_title);
  }

  public String getPersonEmail() {
    return person_email;
  }

  public void setPersonEmail(String _person_email) {
    this.person_email = common_check(this.person_email, _person_email);
  }

  public String getPersonTel() {
    return person_tel;
  }

  public void setPersonTel(String _person_tel) {
    this.person_tel = common_check(this.person_tel, _person_tel);
  }

  public String getDeptName() {
    return dept_name;
  }

  public void setDeptName(String _dept_name) {
    this.dept_name = common_check(this.dept_name, _dept_name);
  }

  public String getOfficeZip() {
    return office_zip;
  }

  public void setOfficeZip(String _office_zip) {
    this.office_zip = common_check(this.office_zip, _office_zip);
  }

  public String getOfficeAddress() {
    return office_address;
  }

  public void setOfficeAddress(String _office_address) {
    this.office_address = common_check(this.office_address, _office_address);
  }

  public String getOfficeTel() {
    return office_tel;
  }

  public void setOfficeTel(String _office_tel) {
    this.office_tel = common_check(this.office_tel, _office_tel);
  }

  public String getOfficeFax() {
    return office_fax;
  }

  public void setOfficeFax(String _office_fax) {
    this.office_fax = common_check(this.office_fax, _office_fax);
  }

  public String getCompanyName() {
    return company_name;
  }

  public void setCompanyName(String _company_name) {
    this.company_name = common_check(this.company_name, _company_name);
  }

  public String getCompanyUrl() {
    return company_url;
  }

  public void setCompanyUrl(String _company_url) {
    this.company_url = common_check(this.company_url, _company_url);
  }

  public String detail() {
    return  "管理番号: " + id + '\n' +
            "氏　　名: " + person_lname + ' ' + person_fname + '\n' +
            "役　　職: " + person_title + '\n' +
            "メ ー ル: " + person_email + '\n' +
            "直通電話: " + person_tel + '\n' +
            "部　　署: " + dept_name + '\n' +
            "郵便番号: " + office_zip + '\n' +
            "住　　所: " + office_address + '\n' +
            "代表電話: " + office_tel + '\n' +
            "代表FAX : " + office_fax + '\n' +
            "会 社 名: " + company_name + '\n' +
            "W  e  b : " + company_url + '\n';
  }

  
  public String common_check(String original_str, String str) {
	
    if (Objects.equals(original_str, str)) {
      return original_str;
    } else {
      String result = check_null(original_str, str);
      result = check_del(result);
      return result;
    }
    
  }
  
  public String check_null(String original_str, String str) {
    if (Objects.isNull(str) || str.equals("")) {
      return original_str;
    }else {
      return str;
    }
  }
  
  public String check_del(String str) {
    if (Objects.equals(str, "*DEL*")) {
      return "";
    } else {
      return str;
    } 
  }


  @Override 
  public String toString() {
    return person_lname + " " + person_fname + " " + company_name +" " + person_title;
  }



}
