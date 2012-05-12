package uniform.view.bean;

import java.io.File;

import javax.faces.bean.ManagedBean;

import uniform.util.email.SmtpServerConfiguration;

@ManagedBean
public class EmailSettingsBackingBean extends AbstractBackingBean {

	private Boolean starttlsEnable;

	private String host = "";

	private String port = "";

	private Boolean authentication;

	private String user = "";

	private String password = "";

	public void init() {
		String filePathAndName = this.getClass().getResource("/").getFile();
		filePathAndName = filePathAndName.replaceFirst("WEB-INF/classes/", "WEB-INF/smtp.cfg.xml");
		SmtpServerConfiguration ssc = new SmtpServerConfiguration(new File(filePathAndName));

		starttlsEnable = Boolean.valueOf(ssc.getStarttlsEnable());
		host = ssc.getHost();
		port = ssc.getPort();
		authentication = Boolean.valueOf(ssc.getAuthentication());
		user = ssc.getUser();
		password = ssc.getPassword();
	}

	public Boolean getStarttlsEnable() {
		return starttlsEnable;
	}

	public void setStarttlsEnable(Boolean starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Boolean getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Boolean authentication) {
		this.authentication = authentication;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String modifyAction() {
		String filePathAndName = this.getClass().getResource("/").getFile();
		filePathAndName = filePathAndName.replaceFirst("WEB-INF/classes/", "WEB-INF/smtp.cfg.xml");
		SmtpServerConfiguration ssc = new SmtpServerConfiguration(new File(filePathAndName));
		ssc.modifyConfiguration(starttlsEnable.toString(), host, port, authentication.toString(), user, password);
		return "emailSettings?faces-redirect=true";
	}

}
