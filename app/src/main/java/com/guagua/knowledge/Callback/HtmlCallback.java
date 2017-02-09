package com.guagua.knowledge.Callback;


import java.util.List;


/**
 * Created by tusalin on 9/7/2016.
 */

public interface HtmlCallback<T> {
      void   onSuccessParseHtml(List<T> obj);
      void onFailedParseHtml(String  error);
}
