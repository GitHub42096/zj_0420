package utils.bisai;

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
	private Map fields=new HashMap();

	public ClassDefn(Class clazz){
		this.name=clazz.getName();
		this.clazz = clazz;
		loadClassDefn(clazz);
	}
	private void loadClassDefn(Class clazz){
		log.debug("开始加载类定义："+clazz.getName());
		Constructor constructors[]= clazz.getConstructors();
		for (Constructor constructor : constructors) {
			log.debug("缓存构造方法：" + constructor.getName() + ReflectUtil.getParmTypesDesc(constructor.getParameterTypes()));
			this.constructors.put(ReflectUtil.getParmTypesDesc(constructor.getParameterTypes()), constructor);
		}

		Field fileds[]=clazz.getFields();
		for (Field filed : fileds) {
			log.debug("缓存成员变量：" + filed.getName());
			this.fields.put(filed.getName(), filed);
		}

		final BeanInfo bi;
		try {
			bi = Introspector.getBeanInfo(clazz);
		} catch(IntrospectionException e) {
			throw new SysLevelException(e);
		}
		final PropertyDescriptor[] pd = bi.getPropertyDescriptors();
		for (PropertyDescriptor aPd : pd) {
			log.debug("缓存成员属性：" + aPd.getName());
			props.put(aPd.getName(), aPd);
		}

		Method methods[]=clazz.getMethods();
		for (Method method : methods) {
			log.debug("缓存成员方法：" + method.getName() + ReflectUtil.getParmTypesDesc(method.getParameterTypes()));
			this.methods.put(method.getName() + ReflectUtil.getParmTypesDesc(method.getParameterTypes()), method);
		}
		log.debug("加载类定义："+clazz.getName()+"成功");
	}
  public Class getClazz() {
    return clazz;
  }
  public Map getConstructors() {
    return constructors;
  }
  public Map getFields() {
    return fields;
  }
  public Map getMethods() {
    return methods;
  }
  public String getName() {
    return name;
  }
  public Map getProps() {
    return props;
  }
}
