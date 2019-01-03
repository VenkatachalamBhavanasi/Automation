package com.ssp.tsl.clients.model;

public class Country {
	private Integer id;
    private String code;
    private String name;
  //  private boolean postCodeVisibility;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*public boolean isPostCodeVisibility() {
		return postCodeVisibility;
	}
	public void setPostCodeVisibility(boolean postCodeVisibility) {
		this.postCodeVisibility = postCodeVisibility;
	}
    */

}
