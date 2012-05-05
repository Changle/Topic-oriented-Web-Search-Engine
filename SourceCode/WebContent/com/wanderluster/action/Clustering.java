package com.wanderluster.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanderluster.train.WordAnalyzer;
import com.wanderluster.train.trainData;
import com.wanderluster.util.Constant;

public class Clustering extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2347409623818742568L;

	@Override
	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String clusterDir=Constant.getRootRealPath("cluster");
		String resouceDir=clusterDir+"/resources/";
		String stopPath=clusterDir+"/stopwords";
		String trainDir=clusterDir+"/cluster_text/";

		
		WordAnalyzer a = new WordAnalyzer(resouceDir,stopPath,trainDir);
		a.loadIDF();
		a.loadFeatureFromFile();
		a.loadGroupVectorFromFile();

		//String s1 = "The conduct of UBS and its employees “corrupted the competitive process and harmed municipalities, and ultimately taxpayers, nationwide,” said Assistant Attorney General Christine A. Varney, who oversees the federal antitrust division. Today’s agreements with UBS ensure that restitution is paid to the victims of the anticompetitive conduct, that UBS pays penalties and disgorges its ill-gotten gains. UBS said in a statement that it was pleased to have resolved the matter. “The underlying transactions were entered into in a business that no longer exists at UBS, and involved employees who are not longer with the firm,” the statement said. The company also said that it had made provisions for the settlement in prior quarters and it therefore will have no effect on the firm’s future financial results or on any current business of UBS.UBS is the second big banking institution to settle accusations of bid-rigging in the municipal bond derivatives market. In December, Bank of America agreed to pay $137 million in restitution after voluntarily disclosing its anticompetitive conduct and agreeing to cooperate with authorities in further investigations. As part of the broader investigation of bid-rigging in the municipal securities market, the Justice Department has brought criminal charges against 18 former executives of various financial services companies. Nine of the 18 have pleaded guilty, including one former UBS employee. Three other UBS employees also have been charged with criminal activities related to the municipal market.";
		String out=a.matchArticle(content);
		
	//   System.out.println(a.RESOURCE_PATH);
	
		System.out.println(out);
		
		
		//request.setAttribute("pageNumBean", pageBean);
	
		//ActionContext.getContext().getSession().put("pageUrl","SearchAction.action?page=");
		//ActionContext.getContext().getSession().put("rsize",recordCount);
		ActionContext.getContext().getSession().put("result",out);

		//*/
		return SUCCESS;
	}
	

	private String content;

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

}
