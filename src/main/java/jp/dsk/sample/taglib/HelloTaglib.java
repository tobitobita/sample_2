package jp.dsk.sample.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class HelloTaglib extends TagSupport {

	private String value;

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int doStartTag() throws JspException {
		System.out.println("doStartTag");
		try {
			this.pageContext.getOut().print("<h1>Hello");
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		System.out.println("doEndTag");
		try {
			this.pageContext.getOut().print(String.format(" %s</h1>", this.value));
		} catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}
}
