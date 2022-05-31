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
		System.out.println("3.機能3");
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
			System.out.println("機能3を実行します");
			break;
		case 9:
			System.out.println("終了します");
			break;
		}
	}

	public static void index() {
		int count = 0;
		try {
			List<NameCard> name_cards = NameCard.selectAll();
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
				index();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	

	}

	public static void show (int id) {
		System.out.println("詳細を表示します");

		try {
			NameCard name_card = NameCard.select(id);
			System.out.println(name_card.detail());

			System.out.println("何をしますか？ enter:メニューに戻る, d:削除");
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();

			if (input.matches("[dD]")) {
				System.out.println("本当に削除しますか？ (y/n)");
				String delete_flag = sc.nextLine();
				if (delete_flag.matches("[yY]")) {
					delete(name_card.getId());
				} else {
					System.out.println("削除をキャンセルしました");
					show(id);
				}
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
		NameCard name_card = input();

		try {
			NameCard.insert(name_card);
			mainmenu();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static NameCard input() {
		NameCard name_card = new NameCard();

		System.out.println("新たな名刺に関する情報を入力てください");

		System.out.println("姓（必須）");
		Scanner sc = new Scanner(System.in);
		name_card.setPersonLname(sc.nextLine());

		System.out.println("名（必須）");
		name_card.setPersonFname(sc.nextLine());

		System.out.println("会社名（必須）");
		name_card.setCompanyName(sc.nextLine());

		System.out.println("会社URL");
		name_card.setCompanyUrl(sc.nextLine());

		System.out.println("事務所郵便番号");
		name_card.setOfficeZip(sc.nextLine());

		System.out.println("事務所住所");
		name_card.setOfficeAddress(sc.nextLine());

		System.out.println("事務所TEL");
		name_card.setOfficeTel(sc.nextLine());

		System.out.println("事務所FAX");
		name_card.setOfficeFax(sc.nextLine());

		System.out.println("部署名");
		name_card.setDeptName(sc.nextLine());

		System.out.println("役職名");
		name_card.setPersonTitle(sc.nextLine());

		System.out.println("個人メール");
		name_card.setPersonEmail(sc.nextLine());

		System.out.println("個人TEL");
		name_card.setPersonTel(sc.nextLine());

		return name_card;
	}

	



}