package manager;

import java.util.ArrayList;

import dao.MemoDaoManager;
import vo.UserAccount;
import vo.UserMemo;

public class MemoManager {

	private MemoDaoManager mdm = null;
	private String id;// 로그인한 ID
	private String name;// 로그인한 이용자 이름

	public MemoManager() {
		this.mdm = new MemoDaoManager();
	}

	public boolean insertAccount(UserAccount user) { //회원가입
		boolean result = false;
		result = mdm.insertAccount(user);
		return result;
	}

	public boolean deleteAccount(UserAccount user) { //회원탈퇴
		boolean result = false;
		result = mdm.deleteAccount(user);
		return result;
	}

	public UserAccount loginAccount(UserAccount user) { //3. 로그인
		UserAccount result = null;
		result = mdm.loginAccount(user);
		
		if (result != null) {
			this.id = result.getId();
			this.name = result.getName();
		}
		return result;
	}
	
	public boolean insertMemo(UserMemo memo) { //글쓰기
		boolean result = false;
		memo.setId(this.id);
		result = mdm.insertMemo(memo);
		return result;
	}
	
	public ArrayList<UserMemo> getMemoList() {
		
		ArrayList<UserMemo> result = null; 
		
		result = mdm.getMemoList(this.id);
		
		return result;
		
	}
	
	
	public ArrayList<UserMemo> searchMemo(String searchword) {
		ArrayList<UserMemo> result = null;
		result = mdm.searchMemo(searchword, this.id);
		return result;
	}

	
	public UserMemo searchOneMemo(String seq) {
	
		UserMemo result = null;
		result = mdm.searchOneMemo(seq, this.id);
		return result;
		
	}

	
	
	
	public boolean updateMemo(String seq, UserMemo memo) {
		boolean result = false;
		result = mdm.updateMemo(seq,memo);
		return result;
	}

	
	public boolean deleteMemo(String seq) {
		boolean result = false;
		result = mdm.deleteMemo(seq);
		return result;
	}

	
	
	public boolean deleteAllMemo(String id) {
		boolean result = false;
		result = mdm.deleteAllMemo(id);
		return result;
	}

	
	
	
	
	
	
	public boolean logout() {

		if (this.name == null) {
			return false;
		}

		this.name = null;
		this.id = null;
		return true;
	}

	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
