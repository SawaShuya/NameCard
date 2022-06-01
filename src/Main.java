import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		mainmenu();
	}

	// controller/actions
	public static void index() {
		try {
			List<NameCard> name_cards = NameCard.selectAll();
			print_index(name_cards);
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}

	public static void show (int id) {
		System.out.println("詳細を表示します");

		try {
			NameCard name_card = NameCard.select(id);
			print_show(name_card);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void delete(int id) {
		try {
			NameCard.delete(id);
			mainmenu();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void create() {
		
		System.out.println("新たな名刺に関する情報を入力てください");
		NameCard name_card = new NameCard();
		input(name_card);

		try {
			NameCard.insert(name_card);
			mainmenu();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void update(int id) {
		System.out.println("更新情報を入力してください");
		try {
			NameCard name_card = NameCard.select(id);
			input(name_card);
			NameCard.update(name_card);
			mainmenu();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void search() {
		System.out.print("検索したい文字列を入力 > ");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();

		try {
			List<NameCard> name_cards = NameCard.search_for(input);
			print_index(name_cards);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	// views
	public static void mainmenu() {
		System.out.println("名刺管理システム\n");
		System.out.println("<<メインメニュー>>");
		System.out.println("1.名前一覧の表示");
		System.out.println("2.名刺情報の追加");
		System.out.println("3.名刺情報の検索");
		System.out.println("9.終了");
		System.out.println("何をしますか？(1-9: 機能の実行)");

		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();

		switch (input) {
		case 1:
			System.out.println("名刺一覧を表示します");
			index();
			break;
		case 2:
			System.out.println("名刺情報の追加します");
			create();
			break;
		case 3:
			System.out.println("名刺情報の検索します");
			search();
			break;
		case 9:
			System.out.println("終了します");
			break;
		default:
			System.out.println("正しく入力してください");
			mainmenu();
			break;
		}
	}
	
	public static NameCard input(NameCard name_card) {
		Scanner sc = new Scanner(System.in);

		while(true) {
			print_item("姓", name_card.getPersonLname());
			String last_name = sc.nextLine();
			if (is_present(name_card.getPersonLname(), last_name) ){
				name_card.setPersonLname(last_name);
				break;
			}
		}

		while(true) {
			print_item("名", name_card.getPersonFname());
			String first_name = sc.nextLine();
			if (is_present(name_card.getPersonFname(), first_name) ){
				name_card.setPersonFname(first_name);
				break;
			}
		}

		while(true) {
			print_item("会社名", name_card.getCompanyName());
			String company_name = sc.nextLine();
			if (is_present(name_card.getPersonFname(), company_name) ){
				name_card.setCompanyName(company_name);
				break;
			}
		}

		print_item("会社URL", name_card.getCompanyUrl());
		name_card.setCompanyUrl(sc.nextLine());

		while(true) {
			print_item("事務所郵便番号", name_card.getOfficeTel());
			String office_zip = sc.nextLine();
			if (office_zip.equals("") || office_zip.equals("*DEL*") || is_zipcode(office_zip)) {
				name_card.setOfficeZip(office_zip);
				break;
			}
		}

		print_item("事務所住所", name_card.getOfficeAddress());
		name_card.setOfficeAddress(sc.nextLine());

		while(true){
			print_item("事務所TEL", name_card.getOfficeTel());
			String office_tel = sc.nextLine();
			if (office_tel.equals("") || is_telephone(office_tel)) {
				name_card.setOfficeTel(office_tel);
				break;
			}
		}

		while(true){
			print_item("事務所FAX", name_card.getOfficeFax());
			String office_fax = sc.nextLine();
			if (office_fax.equals("") || is_telephone(office_fax)) {
				name_card.setOfficeFax(office_fax);
				break;
			}
		}

		print_item("部署名", name_card.getDeptName());
		name_card.setDeptName(sc.nextLine());

		print_item("役職名", name_card.getPersonTitle());
		name_card.setPersonTitle(sc.nextLine());

		while(true){
			print_item("個人メール", name_card.getPersonEmail());
			String person_email = sc.nextLine();
			if (person_email.equals("") || is_email(person_email)) {
				name_card.setPersonEmail(person_email);
				break;
			}
		}

		while(true){
			print_item("個人TEL", name_card.getPersonTel());
			String person_tel = sc.nextLine();
			if (person_tel.equals("") || is_telephone(person_tel)) {
				name_card.setPersonTel(person_tel);
				break;
			}
		}

		return name_card;
	}

	public static void print_index(List<NameCard> name_cards) {
		int count = 0;
		for (NameCard name_card : name_cards) {
			count++;
			System.out.print(count + ". ");
			System.out.println(name_card);
		}

		if (count == 0) {
			System.out.println("名刺はありません, enter:メニューに戻る");
		} else if (count == 1){
			System.out.println("何をしますか？ 1: 詳細を見る, enter:メニューに戻る)");
		} else {
			System.out.println("何をしますか？ 1-" + count + ": 詳細を見る, enter:メニューに戻る)");
		}
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();

		if (input.matches("[0-9]{1,}") && Integer.parseInt(input) <= count) {
			int num = Integer.parseInt(input) - 1;
			show(name_cards.get(num).getId());
		} else if (input.matches("")) {
			mainmenu();
		} else {
			System.out.println("入力が正しくありません。");
			mainmenu();
		}
	}

	public static void print_show(NameCard name_card) {
		int id = name_card.getId();
			System.out.println(name_card.detail());

			System.out.println("何をしますか？ enter:メニューに戻る, d:削除 u:更新");
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();

			if (input.matches("[dD]")) {
				System.out.println("本当に削除しますか？ (y/n)");
				String delete_flag = sc.nextLine();
				if (delete_flag.matches("[yY]")) {
					delete(id);
				} else {
					System.out.println("削除をキャンセルしました");
					show(id);
				}
			}else if(input.matches("[uU]")) {
				update(id);

			} else if (input.matches("")) {
				mainmenu();
			} else {
				System.out.println("入力が正しくありません。");
				show(id);
			}

		
	}

	// check methods
	public static boolean is_present(String original_str, String str) {
		if (str.matches("") && (Objects.isNull(original_str) || original_str.matches(""))) {
			System.out.println("入力が正しくありません。");
			return false;
		} else {
			return true;
		}
	}

	public static boolean is_zipcode(String office_zip) {
		if (office_zip.matches("[0-9]{3}-?[0-9]{4}")) {
			return true;
		} else {
			System.out.println("入力が正しくありません。");
			return false;
		}
	}

	public static boolean is_telephone(String office_tel) {
		if (office_tel.matches("[0-9()-]{7,}")) {
			return true;
		} else {
			System.out.println("入力が正しくありません。");
			return false;
		}
	}

	public static boolean is_email(String person_email) {
		if (person_email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
			return true;
		} else{
			System.out.println("入力が正しくありません。");
			return false;
		}
	}
	
	// display methods
	public static void print_item(String column, String original) {
		String nesesity = "";

		if (column.matches("姓|名|会社名")) {
			nesesity = " *必須 ";
		}

		if (original == null) {
			System.out.print(column + nesesity + " > ");
		} else {
			System.out.print(column + nesesity + " (saved: " + original + ") > " );
		}
		
	}


}