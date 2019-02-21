package utils.excel_read_write;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ClassDefn {
    private static Log log = LogFactory.getLog(ClassDefn.class);
    private String name;
    private Class clazz;
    private Map constructors = new HashMap();
    private Map methods = new HashMap();
    private Map props = new HashMap();
    private Map fields = new HashMap();

    public ClassDefn(Class clazz) {
        this.name = clazz.getName();
        this.clazz = clazz;
        this.loadClassDefn(clazz);
    }

    private void loadClassDefn(Class clazz) {
        log.debug("开始加载类定义：" + clazz.getName());
        Constructor[] constructors = clazz.getConstructors();
        Constructor[] var3 = constructors;
        int var4 = constructors.length;

        int var5;
        for(var5 = 0; var5 < var4; ++var5) {
            Constructor constructor = var3[var5];
            log.debug("缓存构造方法：" + constructor.getName() + ReflectUtil.getParmTypesDesc(constructor.getParameterTypes()));
            this.constructors.put(ReflectUtil.getParmTypesDesc(constructor.getParameterTypes()), constructor);
        }

        Field[] fileds = clazz.getFields();
        Field[] var13 = fileds;
        var5 = fileds.length;

        for(int var16 = 0; var16 < var5; ++var16) {
            Field filed = var13[var16];
            log.debug("缓存成员变量：" + filed.getName());
            this.fields.put(filed.getName(), filed);
        }

        BeanInfo bi;
        try {
            bi = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException var11) {
            throw new SysLevelException(var11);
        }

        PropertyDescriptor[] pd = bi.getPropertyDescriptors();
        PropertyDescriptor[] var17 = pd;
        int var18 = pd.length;

        int var8;
        for(var8 = 0; var8 < var18; ++var8) {
            PropertyDescriptor aPd = var17[var8];
            log.debug("缓存成员属性：" + aPd.getName());
            this.props.put(aPd.getName(), aPd);
        }

        Method[] methods = clazz.getMethods();
        Method[] var20 = methods;
        var8 = methods.length;

        for(int var21 = 0; var21 < var8; ++var21) {
            Method method = var20[var21];
            log.debug("缓存成员方法：" + method.getName() + ReflectUtil.getParmTypesDesc(method.getParameterTypes()));
            this.methods.put(method.getName() + ReflectUtil.getParmTypesDesc(method.getParameterTypes()), method);
        }

        log.debug("加载类定义：" + clazz.getName() + "成功");
    }

    public Class getClazz() {
        return this.clazz;
    }

    public Map getConstructors() {
        return this.constructors;
    }

    public Map getFields() {
        return this.fields;
    }

    public Map getMethods() {
        return this.methods;
    }

    public String getName() {
        return this.name;
    }

    public Map getProps() {
        return this.props;
    }
}
