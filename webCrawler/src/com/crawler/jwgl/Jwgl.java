package com.crawler.jwgl;

<<<<<<< HEAD
<<<<<<< HEAD
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
=======
import java.io.IOException;
import java.io.InputStream;
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
=======
import java.io.IOException;
import java.io.InputStream;
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

<<<<<<< HEAD
<<<<<<< HEAD
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
=======
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
=======
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
<<<<<<< HEAD
<<<<<<< HEAD
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
=======
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
=======
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.crawler.utils.DateUtils;
import com.crawler.utils.GlobalConstant;
import com.crawler.utils.IOUtils;
import com.crawler.utils.ParseUtils;

public class Jwgl {
	private static String stuNumber = "";
	private static String stuName = "";
	private static String Cookie = "";
	private String indexUrl = GlobalConstant.INDEX_URL;
	private String secretCodeUrl = GlobalConstant.SECRETCODE_URL;
	private String loginUrl = GlobalConstant.LOGIN_URL;
	private String mainUrl = GlobalConstant.MAIN_URL;
	private String queryStuGradeUrl = GlobalConstant.QUERY_STU_GRADE_URL;
	private String queryStuGradeGnmkd = GlobalConstant.QUERY_STU_GRADE_GNMKDM;
	private String queryStuCourseUrl = GlobalConstant.QUERY_STU_COURSE_URL;
	private String queryStuCourseGnmkd = GlobalConstant.QUERY_STU_COURSE_GNMKDM;
	private String identityStu = GlobalConstant.IDENTITY_STU;
	
	private String myMessageUrl = GlobalConstant.MY_MESSAGE_URL;

	private String myMessageGnmkdm = GlobalConstant.MY_MESSAGE_GNMKDM;
<<<<<<< HEAD
<<<<<<< HEAD
	
	private String changePasswordUrl = GlobalConstant.CHANGE_PASSWORD_URL;
	private String changePasswordGnmkdm = GlobalConstant.CHANGE_PASSWORD_GNMKDM;
=======
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
=======
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
	/**
	 * 登录功能
	 * 
	 * @param stuNumber 帐号
	 * @param password	密码
	 * @return
	 * @throws Exception
	 * @throws UnsupportedOperationException
	 */
	public boolean login(String stuNumber, String password)
			throws UnsupportedOperationException, Exception {
		this.stuNumber = stuNumber;
		// 获取验证
		HttpGet secretCodeGet = new HttpGet(secretCodeUrl);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse responseSecret = client.execute(secretCodeGet);
		// 获取返回的Cookie
		Cookie = responseSecret.getFirstHeader("Set-Cookie").getValue();
		String viewState = IOUtils.getViewState(indexUrl, "", "");
		// 将验证码下载到C盘 可根据自己需要更改验证码存储路径
		IOUtils.getSecret(responseSecret.getEntity().getContent(),
				"secretCode.png", "c://");
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入验证码");
		// 手动填充刚才获取的验证码的
		String secret = sc.next().trim();
		HttpPost loginPost = new HttpPost(loginUrl);// 创建登录的Post请求
		loginPost.setHeader("Cookie", Cookie);// 带上第一次请求的Cookie
		List<NameValuePair> nameValuePairLogin = new ArrayList<NameValuePair>();// 封装Post提交参数
		nameValuePairLogin
				.add(new BasicNameValuePair("__VIEWSTATE", viewState));// 隐藏表单
		nameValuePairLogin
				.add(new BasicNameValuePair("txtUserName", stuNumber));// 学号
		nameValuePairLogin.add(new BasicNameValuePair("TextBox2", password));// 密码
		nameValuePairLogin.add(new BasicNameValuePair("txtSecretCode", secret));// 验证
		nameValuePairLogin.add(new BasicNameValuePair("RadioButtonList1",
				identityStu));// 身份,默认学生
		nameValuePairLogin.add(new BasicNameValuePair("Button1", ""));
		nameValuePairLogin.add(new BasicNameValuePair("lbLanguage", ""));
		nameValuePairLogin.add(new BasicNameValuePair("hidPdrs", ""));
		nameValuePairLogin.add(new BasicNameValuePair("hidsc", ""));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
				nameValuePairLogin, "GB2312");
		loginPost.setEntity(entity);
		HttpResponse responseLogin = client.execute(loginPost);
		// client1.close();
		// 判断提交数据是否成功，成功返302
		if (responseLogin.getStatusLine().getStatusCode() == 302) {
			// 如果提交成功，带ookie请求重定向的main页面，并获取学生姓名
			HttpGet mainGet = new HttpGet(mainUrl + stuNumber);
			mainGet.setHeader("Cookie", Cookie);
			mainGet.setHeader("Referer", loginUrl);
			HttpResponse responseMain = client.execute(mainGet);
			InputStream is = responseMain.getEntity().getContent();
			String html = "";
			try {
				html = IOUtils.getHtml(is, "GB2312");
			} catch (Exception e) {
				System.out.println("解析html失败");
				e.printStackTrace();
			}
			stuName = Jsoup.parse(html).getElementById("xhxm").text();
			System.out.println("登录成功！欢迎您" + stuName);
			client.close();
			return true;
		} else {
			System.out.println("登录失败");
			client.close();
			return false;
		}

	}
	/**
	 * 
	 * @param xn 学年
	 * @param xq 学期
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void queryStuGrade(String xn, String xq)
			throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		String newQueryStuGradeUrl = queryStuGradeUrl + stuNumber + "&xm="
				+ stuName + queryStuGradeGnmkd;
		HttpPost queryGradePost = new HttpPost(newQueryStuGradeUrl);
		String viewState = IOUtils.getViewState(newQueryStuGradeUrl, Cookie,
				mainUrl + stuNumber);
		// 封装请求参数
		List<NameValuePair> queryGradePair = new ArrayList<NameValuePair>();
		queryGradePair.add(new BasicNameValuePair("__EVENTTARGET", ""));
		queryGradePair.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		queryGradePair.add(new BasicNameValuePair("__VIEWSTATE", viewState));
		queryGradePair.add(new BasicNameValuePair("hidLanguage", ""));
		queryGradePair.add(new BasicNameValuePair("ddlXN", xn));// 学年
		queryGradePair.add(new BasicNameValuePair("ddlXQ", xq));// 学期
		queryGradePair.add(new BasicNameValuePair("ddl_kcxz", ""));
		queryGradePair.add(new BasicNameValuePair("btn_xq", "学期成绩"));
		queryGradePost.setHeader("Cookie", Cookie);
		queryGradePost.setHeader("Referer", mainUrl + stuNumber);
		UrlEncodedFormEntity entityGrade = new UrlEncodedFormEntity(
				queryGradePair);
		queryGradePost.setEntity(entityGrade);
		HttpResponse responQueryGradePost = client.execute(queryGradePost);

		String gradeHtml = IOUtils.getHtml(responQueryGradePost.getEntity()
				.getContent(), "GB2312");
		// System.out.println(gradeHtml);
		Document gradeDoc = Jsoup.parse(gradeHtml);
		Elements eleGrade = gradeDoc.select("td");
		// 按需求解析html<td>标签内容并输
		for (int i = 0; i < 7; i++) {
			System.out.println(eleGrade.get(i).text());
		}

		for (int i = 11; i < eleGrade.size(); i = i + 10) {
			if (i + 15 < eleGrade.size()) {
				System.out.print(eleGrade.get(i).text() + "				");
				i = i + 5;
				System.out.print(eleGrade.get(i).text());
				System.out.println();
			}
			client.close();

		}

	}

	/**
	 * 查询个人课表方法
	 * 
	 * @param xnd 学年
	 * @param xqd 学期
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void queryStuCourse(String xnd, String xqd)
			throws ClientProtocolException, IOException {
		
		CloseableHttpClient client = HttpClients.createDefault();
		String newQueryStuCourseUrl = queryStuCourseUrl + stuNumber + "&xm="
				+ stuName + queryStuCourseGnmkd;
		
		String viewState = IOUtils.getViewState(newQueryStuCourseUrl, Cookie,
				mainUrl + stuNumber);
		
		HttpPost queryStuCoursePost = new HttpPost(newQueryStuCourseUrl);
		
		List<NameValuePair> stuCoursePair = new ArrayList<NameValuePair>();
		stuCoursePair.add(new BasicNameValuePair("__EVENTTARGET", "xqd"));
		stuCoursePair.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		stuCoursePair.add(new BasicNameValuePair("__VIEWSTATE", viewState));
		stuCoursePair.add(new BasicNameValuePair("xnd", xnd));
		stuCoursePair.add(new BasicNameValuePair("xqd", xqd));
<<<<<<< HEAD
<<<<<<< HEAD
		
		 
		/*MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		 builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		 FileBody fileBody = new FileBody(new File("C:\\secretCode.png"));
		 builder.addPart("my_file", fileBody);
		 HttpEntity entity = builder.build();*/

		
		 
		UrlEncodedFormEntity entitySource = new UrlEncodedFormEntity(
				stuCoursePair);
		
=======
		UrlEncodedFormEntity entitySource = new UrlEncodedFormEntity(
				stuCoursePair);
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
=======
		UrlEncodedFormEntity entitySource = new UrlEncodedFormEntity(
				stuCoursePair);
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
		queryStuCoursePost.setEntity(entitySource);
		
		queryStuCoursePost.setHeader("Cookie", Cookie);
		queryStuCoursePost.setHeader("Referer", mainUrl + stuNumber);
		HttpResponse responseStuCourse = client.execute(queryStuCoursePost);
		String html = IOUtils.getHtml(responseStuCourse.getEntity()
				.getContent(), "GB2312");
		Document docCourse = Jsoup.parse(html);
		Elements eleCourse = docCourse.select("td");
		for (int i = 2; i < eleCourse.size(); i++) {
			System.out.print(eleCourse.get(i).text() + "	");
			if (i % 9 == 0) {
				System.out.println();
			}
		}
	}
	/**
	 * 获取个人信息
	 * @param sh
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void queryMymessage(String sh) throws ClientProtocolException, IOException
	{
		CloseableHttpClient client = HttpClients.createDefault();
		String newQueryStuCourseUrl = myMessageUrl + stuNumber + "&xm="
				+ stuName + myMessageGnmkdm;
		
		HttpPost queryStuCoursePost = new HttpPost(newQueryStuCourseUrl);
		queryStuCoursePost.setHeader("Cookie", Cookie);
		queryStuCoursePost.setHeader("Referer", mainUrl + stuNumber);
		HttpResponse responseStuCourse = client.execute(queryStuCoursePost);
		String html = IOUtils.getHtml(responseStuCourse.getEntity()
				.getContent(), "GB2312");
<<<<<<< HEAD
<<<<<<< HEAD
		Document docCourse = Jsoup.parse(html);		
		Elements eleCourse = docCourse.select("td");
		for (int i = 0; i < eleCourse.size(); i++) {
			System.out.print(eleCourse.get(i).text());
			System.out.print(eleCourse.get(i).select("img").attr("src"));		
			System.out.println(eleCourse.get(i).select("input").attr("value"));
=======
=======
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
		Document docCourse = Jsoup.parse(html);
		Elements eleCourse = docCourse.select("td");
		for (int i = 0; i < eleCourse.size(); i++) {
			System.out.print(eleCourse.get(i).text());
			System.out.print(eleCourse.get(i).select("img").attr("src"));
			System.out.print(eleCourse.get(i).select("input").attr("value"));
<<<<<<< HEAD
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
=======
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
			if (i % 2 == 0&&i!=0) {
				System.out.println();
			}
		}
	}
	
	
<<<<<<< HEAD
<<<<<<< HEAD

	
	/**
	 * 更改教务处密码
	 * @param xh学号 
	 * oldPassword 教务处原始密码
	 * newPassword 新密码
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void updatePassword(String xh,String oldPassword,String newPassword) throws ClientProtocolException, IOException
	{
		CloseableHttpClient client = HttpClients.createDefault();
		String newQueryStuGradeUrl = changePasswordUrl+xh+changePasswordGnmkdm;
		HttpPost queryGradePost = new HttpPost(newQueryStuGradeUrl);
		String viewState = IOUtils.getViewState(newQueryStuGradeUrl, Cookie,
				mainUrl + stuNumber);
		// 封装请求参数
		List<NameValuePair> queryGradePair = new ArrayList<NameValuePair>();
		queryGradePair.add(new BasicNameValuePair("__VIEWSTATE", viewState));
		queryGradePair.add(new BasicNameValuePair("TextBox2", oldPassword));
		queryGradePair.add(new BasicNameValuePair("TextBox3", newPassword));
		queryGradePair.add(new BasicNameValuePair("TextBox4", newPassword));
		queryGradePair.add(new BasicNameValuePair("Button1", "修  改"));
		
		queryGradePost.setHeader("Cookie", Cookie);
		queryGradePost.setHeader("Referer",newQueryStuGradeUrl);
		queryGradePost.setHeader("Host", "jwgl.fjnu.edu.cn");
		queryGradePost.setHeader("Origin", "http://jwgl.fjnu.edu.cn");
		UrlEncodedFormEntity entityGrade = new UrlEncodedFormEntity(
				queryGradePair);
		queryGradePost.setEntity(entityGrade);
		
		HttpResponse responQueryGradePost = client.execute(queryGradePost);

		String gradeHtml = IOUtils.getHtml(responQueryGradePost.getEntity()
				.getContent(), "GB2312");
		 Document docCourse = Jsoup.parse(gradeHtml);		
			Elements eleCourse = docCourse.select("script");
		 System.out.println(eleCourse.get(0));
	}

	
	
=======
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
=======
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
	
	public static void main(String[] args) {
		Jwgl jw = new Jwgl();
		try {
<<<<<<< HEAD
<<<<<<< HEAD
			jw.login("学号", "密码");
			/* System.out.println("测试查询学年成绩------");
			 jw.queryStuGrade("2016-2017", "1");*/
			/* System.out.println("查询个人课表测试-------");
			 jw.queryStuCourse("2015-2016", "1");*/
			jw.updatePassword("学号","原始密码","新密码");
=======
=======
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
			jw.login("123012014080", "ljm18750037695");
			/* System.out.println("测试查询学年成绩------");
			 jw.queryStuGrade("2016-2017", "1");
			 System.out.println("查询个人课表测试-------");
			 jw.queryStuCourse("2015-2016", "1");*/
			jw.queryMymessage("123012014080");
<<<<<<< HEAD
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
=======
>>>>>>> b36de55e55427063626017569e84bf29eee05f22
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
