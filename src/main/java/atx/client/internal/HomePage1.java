package atx.client.internal;

import atx.client.model.AndroidElement;

public class HomePage1 {

    @FindElementBy(id=":id/first_text",verify = true)
    public AndroidElement home_bottom;

    @FindElementBy(text="我的")
    public AndroidElement my;


    public HomePage1() {
        super();
    }



}