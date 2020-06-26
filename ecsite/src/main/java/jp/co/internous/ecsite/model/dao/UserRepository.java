//LoginFormから渡されるユーザ情報(ユーザ名、パスワード)を条件にDB検索するためのDAO

package jp.co.internous.ecsite.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.internous.ecsite.model.entity.User;

public interface UserRepository extends JpaRepository <User, Long>{

//	Repositoryインターフェースに宣言されたメソッドを、
//	その名前からクエリを生成して自動的に生成
	List <User> findByUserNameAndPassword(String userName, String password);
}
