package uniform.bo;

import java.util.ArrayList;
import java.util.List;

import uniform.dao.SearchEngineDAO;
import uniform.entity.Announcement;
import uniform.entity.Article;
import uniform.entity.News;

public class SearchEngineBO {

	SearchEngineDAO searchEngineDAO = new SearchEngineDAO();

	public List<Article> searchInArticles(String rawKeywords) {
		if (getKeywords(rawKeywords).size() == 0) {
			return new ArrayList<Article>();
		}
		return searchEngineDAO.searchInArticles(getKeywords(rawKeywords));
	}
	
	public List<News> searchInNews(String rawKeywords) {
		if (getKeywords(rawKeywords).size() == 0) {
			return new ArrayList<News>();
		}
		return searchEngineDAO.searchInNews(getKeywords(rawKeywords));
	}
	
	public List<Announcement> searchInAnnouncements(String rawKeywords) {
		if (getKeywords(rawKeywords).size() == 0) {
			return new ArrayList<Announcement>();
		}
		return searchEngineDAO.searchInAnnouncements(getKeywords(rawKeywords));
	}

	public List<String> getKeywords(String rawKeywords) {
		String[] splitedRawKeywords = rawKeywords.split(" ");

		List<String> keywords = new ArrayList<String>();

		for (String prospectKeyword : splitedRawKeywords) {
			
			prospectKeyword = prospectKeyword.trim();
			
			if (!prospectKeyword.isEmpty()
				&& prospectKeyword.length() > 1) {
				keywords.add(prospectKeyword);
			}
		}

		return keywords;
	}

}
