package cn.cwiz.annotation;

@ForClass("http://forClass.cn")
public class Anno {
    @ForField("testing")
    private String name;

    @ForMethod()
    public String getDefault() {
        return "get default Annotation";
    }
    @ForMethod(name="desktophrm",url="desktophrm.com")
    public String getDefine() {
        return "get define Annotation";
    }
}
