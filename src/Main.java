import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		mainmenu();
	}

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

	public static NameCard input(NameCard name_card) {
		Scanner sc = new Scanner(System.in);

		while(true) {
			print_item("姓", name_card.getPersonLname());
			String last_name = sc.nextLine();
			if (is_present(name_card.getPersonFname(), last_name) ){
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

		print_item("事務所郵便番号", name_card.getOfficeTel());
		name_card.setOfficeZip(sc.nextLine());

		print_item("事務所住所", name_card.getOfficeAddress());
		name_card.setOfficeAddress(sc.nextLine());

		print_item("事務所TEL", name_card.getOfficeTel());
		name_card.setOfficeTel(sc.nextLine());

		print_item("事務所FAX", name_card.getOfficeFax());
		name_card.setOfficeFax(sc.nextLine());

		print_item("部署名", name_card.getDeptName());
		name_card.setDeptName(sc.nextLine());

		print_item("役職名", name_card.getPersonTitle());
		name_card.setPersonTitle(sc.nextLine());

		print_item("個人メール", name_card.getPersonEmail());
		name_card.setPersonEmail(sc.nextLine());

		print_item("個人TEL", name_card.getPersonTel());
		name_card.setPersonTel(sc.nextLine());

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

	public static boolean is_present(String original_str, String str) {
		if (str.matches("") && original_str.matches("")) {
			System.out.println("入力が正しくありません。");
			return false;
		} else {
			return true;
		}
	}
	
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