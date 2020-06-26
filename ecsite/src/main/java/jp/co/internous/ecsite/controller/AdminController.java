//管理者サイトのコントローラー

package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.ecsite.model.dao.GoodsRepository;
import jp.co.internous.ecsite.model.dao.UserRepository;
import jp.co.internous.ecsite.model.entity.Goods;
import jp.co.internous.ecsite.model.entity.User;
import jp.co.internous.ecsite.model.form.GoodsForm;
import jp.co.internous.ecsite.model.form.LoginForm;

@Controller
//　localhost:8080/ecsite/admin/ のURLでアクセスできるよう設定↓
@RequestMapping("/ecsite/admin")
public class AdminController {

//Repositoryを読み込み
	@Autowired
	private UserRepository userRepos;
	
	@Autowired
	private GoodsRepository goodsRepos;
//トップページ(adminindex.html)に遷移するメソッド。
	@RequestMapping("/")
	public String index() {
		return "adminindex";
	}
	
	
//	ユーザ名とパスワードをLoginFormを介して受け取る
	@PostMapping("/welcome")
	public String welcome(LoginForm form, Model m) {
//	  ユーザ名とパスワードでユーザを検索↓
//		UserRepositoryのインスタンスからfindメソッドでリスト取得。
		List <User> users = userRepos.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		
		if(users !=null && users.size() > 0) {
//			isadminが0ではない。つまり管理者である。
			boolean isAdmin = users.get(0).getIsAdmin() !=0;
			if(isAdmin) {//管理者だったら
				//goodsReposのメソッド
				List<Goods> goods = goodsRepos.findAll();
				m.addAttribute("userName", users.get(0).getUserName());
				m.addAttribute("password", users.get(0).getPassword());
				m.addAttribute("goods",goods);
			}
		}
		return "welcome";
	}
	
	@RequestMapping("/goodsMst")
	public String goodsMst(LoginForm form, Model m) {
		m.addAttribute("userName", form.getUserName());
		m.addAttribute("password", form.getPassword());
		
		return "goodsmst";
		
	}
	
	
	@RequestMapping("/addGoods")
	public String addGoods(GoodsForm goodsForm, LoginForm loginForm, Model m) {
		m.addAttribute("userName", loginForm.getUserName());
		m.addAttribute("password", loginForm.getPassword());
		
		Goods goods = new Goods();
		goods.setGoodsName(goodsForm.getGoodsName());
		goods.setPrice(goodsForm.getPrice());
		goodsRepos.saveAndFlush(goods);
		
		return "forward:/ecsite/admin/welcome";
}
	
	@ResponseBody
	@PostMapping("/api/deleteGoods")
	public String deleteApi(@RequestBody GoodsForm f, Model m) {
		try {
			goodsRepos.deleteById(f.getId());
		} catch(IllegalArgumentException e) {
			return "-1";
		}
	
	return "1";
			}
}