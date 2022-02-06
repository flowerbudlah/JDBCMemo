package ui;

import java.util.ArrayList;
import java.util.Scanner;

import manager.MemoManager;
import vo.UserAccount;
import vo.UserMemo;

public class MemoUI {

	private MemoManager manager = new MemoManager(); // 요청과 관련된 처리를 하기 위해 생성한
														// MemoManager 클래스의 객체
	private Scanner sc = new Scanner(System.in);
	private Scanner scLine = new Scanner(System.in);
	private boolean accountflag = true;
	private boolean memoflag = false;

	
	/**
	 * <pre>
	 * 메모 관리 프로그램의 사용자 화면을 구성하고 사용자 입력을 대기한다.
	 * 프로그램은 종료 메뉴를 선택하기 전까지 무한 반복하여 처리 된다.
	 * </pre>
	 */
	public MemoUI() {

		while (accountflag) {
			
			mainMenu();

			switch (sc.nextInt()) {
				case 1: insertAccount(); break;
				case 2: deleteAccount(); break;
				case 3: loginAccount();

				while (memoflag) {
					subMenu();
					switch (sc.nextInt()) {
						case 1: insertMemo(); break;
						case 2: searchMemo(); break;
						case 3: searchAllMemo(); break;
						case 4: searhOneMemo(); break;
						case 5: updateMemo(); break;
						case 6: deleteMemo(); break;
						case 9: memoflag = false; break;	
					}
				}
				break;
				case 9: accountflag = false; break;
			}
		}
	}

	
	public void mainMenu() {
		System.out.println("=================================");
		System.out.println("Soft Engineer Memo");
		System.out.println("=================================");
		System.out.println("1.회원가입");
		System.out.println("2.회원탈퇴");
		System.out.println("3.로그인");
		System.out.println("9.종료");
		System.out.println("=================================");
		System.out.print("메뉴 번호: ");
	}
	
	
	public void subMenu() {
		System.out.println("=================================");
		System.out.println("Soft Engineer Memo");
		System.out.println("=================================");
		System.out.println("1.메모등록");
		System.out.println("2.메모검색(내용+제목)");
		System.out.println("3.메모전체보기(내가 쓴 글 전체보기)");
		System.out.println("4.메모상세보기");
		System.out.println("5.메모수정");
		System.out.println("6.메모삭제");
		System.out.println("9.종료");
		System.out.println("=================================");
		System.out.print("메뉴 번호: ");
	}

	
	
	
	public void loginAccount() {
		
		while (true) {
			
			System.out.print("ID: "); String id = scLine.nextLine();
			System.out.print("Password: "); String password = scLine.nextLine();
		
			if ((id == null || id.equals("")) || (password == null || password.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
				
			} else {
				UserAccount result = manager.loginAccount(new UserAccount(manager.getName(), id, password));
				
				if (result != null) {
					System.out.println("로그인이 완료되었습니다." +manager.getId()+ "(" +manager.getName()+ "님) 환영합니다.");
					memoflag = true;
					break;
					
					
				} else {
					System.out.println("******ERROR 아이디/비밀번호가 일치하지 않습니다."); 
				
				}
			} //else 끝
		} //while 끝
	}

	

	public void insertAccount() {
		while (true) {
			System.out.println("이름: "); String name = scLine.nextLine();
			System.out.println("ID: "); String id = scLine.nextLine();
			System.out.println("비밀번호: "); String password = scLine.nextLine();
			if ((name == null || name.equals("")) || (id == null || id.equals(""))|| (password == null || password.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			} else {
				boolean result = manager.insertAccount(new UserAccount(name, id, password));
				if (result) {
					System.out.println("계정등록 완료");
					break;
				} else {
					System.out.println("******ERROR 같은아이디가 존재합니다."); break; 
				}
			}
		}
	}

	

	public void deleteAccount() {
		while (true) {
			System.out.println("ID: "); String id = scLine.nextLine();
			System.out.println("비밀번호: "); String password = scLine.nextLine();
			
			if ((id == null || id.equals("")) || (password == null || password.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요."); 
			} else {
				manager.deleteAllMemo(id);
				
				boolean result = manager.deleteAccount(new UserAccount(null, id, password));
				if (result) {
					System.out.println("계정삭제 완료");
					break;
				} else {
					System.out.println("******ERROR 삭제실패.");
				}
			}
		}
	}

	
	
	public void insertMemo() { //글쓰기

		while (true) {
			
			System.out.print("제목: "); String title = scLine.nextLine();
			System.out.print("내용: "); String content = scLine.nextLine();
			
			if ((title == null || title.equals("")) || (content == null || content.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			
			} else {
		
				boolean result = manager.insertMemo(new UserMemo(null, title, content, null, null));
				
				if (result) {
					System.out.println("메모등록 완료! "); break;
				} else {
					System.out.println("메모등록에 실패함! "); break; 
				}
			}
		}
	}

	public void searchMemo() {
		while (true) {
			System.out.print("제목 or 내용: "); String searchWord = scLine.nextLine();
			
			if ((searchWord == null || searchWord.equals(""))) {
				System.out.println("빈칸 없이 입력을 해주세요!");
				
			} else {
				
				ArrayList<UserMemo> result = manager.searchMemo(searchWord);
				
				
				if (result != null) {
					printAllMemo(result); break;
				} else {
					System.out.println("******ERROR 메모검색에 실패했습니다."); break;
				}
			}
		}
	}

	
	
	
	public void searchAllMemo() { //자기가 쓴 메모리스트
		while (true) {

			ArrayList<UserMemo> result = manager.getMemoList();
			if (result != null) {
				printAllMemo(result);
				break;
			} else {
				System.out.println("******ERROR 메모조회에 실패했습니다."); 
				break; 
			}
		}
	}

	public void printAllMemo(ArrayList<UserMemo> memos) {
		for (UserMemo memo : memos) {
			System.out.println(memo);
		}
	}
	
	
	
	
	
	public void searhOneMemo() { //메모 일련번호 검색
		while (true) {
			System.out.print("메모 일련번호: "); String memoseq = scLine.nextLine();
			
			if ((memoseq == null || memoseq.equals(""))) {
				
				System.out.print("******ERROR 다시입력해주세요.");
			} else {
				
				UserMemo result = manager.searchOneMemo(memoseq);
		
				System.out.println();
				
				if (result != null) {
					System.out.println("제목: "+result.getTitle());
					System.out.println("내용: "+result.getContent());
					break;
					
				} else {
					System.out.println("******ERROR 메모검색에 실패했습니다.");
				}
			}
		}
	}

	
	
	
	public void deleteMemo() {
		
		while (true) {
			
			System.out.print("삭제할 메모 일련번호: "); String memoseq = scLine.nextLine();
			
			if ((memoseq == null || memoseq.equals(""))) {
				
				System.out.println("******ERROR 다시입력해주세요.");
			} else {
				
				boolean result = manager.deleteMemo(memoseq);
				
				if (result) {
					System.out.println("삭제에 성공했습니다."); break;
				} else {
					System.out.println("******ERROR 메모삭제에 실패했습니다.");
				}
			}
		}
	}
	
	
	public void updateMemo(){
		while (true) {
			
			System.out.println("수정할 메모 일련번호: "); String memoseq = scLine.nextLine();
			
			if ((memoseq == null || memoseq.equals(""))) {
				System.out.println("******ERROR 다시입력해주세요.");
			} else {
				System.out.print("제목: "); String title = scLine.nextLine();
				System.out.print("내용: "); String content = scLine.nextLine();
				
				if ((title == null || title.equals("")) || (content == null || content.equals(""))) {
					System.out.println("******ERROR 다시입력해주세요.");
				} else {
					boolean result = manager.updateMemo(memoseq,new UserMemo(null, title, content, null, null));
					
					if (result) {
						System.out.println("메모수정 완료");
						break;
					} else {
						System.out.println("******ERROR 메모수정에 실패했습니다.");
					}
				}
			}
		}	
	}


	
}
