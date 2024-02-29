package net.xayanix.nssv.reallife.family;

import net.xayanix.nssv.core.utils.ChatUtil;

public enum FamilyRank {
	
	OWNER("Gl. rodziny", "&4"),
	MOTHER("Mama", "&2"),
	DAD("Tata", "&c"),
	DAUGHTER("Córka", "&d"),
	SON("Syn", "&b"),
	FRIEND("Znajomy", "&3"),
	GRANDMA("Babcia", "&7"),
	GRANDAD("Dziadek", "&8"),
	AUNT("Ciocia", "&6"),
	UNCLE("Wujek", "&e"),
	UNKNOWN("Czl. rodz.", "&f"),
	CAT("Kot", "&e"),
	DOG("Pies", "&e");
	
	private String description;
	private String color;
	
	FamilyRank(String description, String color){
		this.description = description;
		this.color = ChatUtil.fixColors(color);
		FamilyManager.ranks.add(this);
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public String getPrefix(){
		return (this.color + this.description);
	}
	
}
