package com.lipiji.textrefiner;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class TextRefiner {
    private static final String PUNCTUATION = "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]";

    public static String filterPunctuation(String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        } else {
            return text.replaceAll(PUNCTUATION, "");
        }
    }
    
    //http://stackoverflow.com/a/21430792/160698
    public static List<String> toSentences(String text) {
        Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(text)) {
            return list;
        }
        Matcher reMatcher = re.matcher(text);
        while (reMatcher.find()) {
            list.add(reMatcher.group());
        }
        return list;
    }
    
    public static List<String> toSentenceByStanfordNLP(String text) {
        Reader reader = new StringReader(text);
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);
        List<String> sentenceList = new ArrayList<>();

        for (List<HasWord> sentence : dp) {
            String sentenceString = Sentence.listToString(sentence);
            sentenceList.add(sentenceString.toString());
        }
        return sentenceList;
    }
}
