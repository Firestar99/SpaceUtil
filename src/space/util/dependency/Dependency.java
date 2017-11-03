package space.util.dependency;

import space.util.baseobject.BaseObject;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class Dependency implements BaseObject, IDependency {
	
	static {
		BaseObject.initClass(Dependency.class, Dependency::new, d -> new Dependency(d.uuid, d.requires, d.requiredBy, d.defaultPriority));
	}
	
	public String uuid;
	public String[] requires;
	public String[] requiredBy;
	public int defaultPriority;
	
	public Dependency() {
	}
	
	public Dependency(String uuid) {
		this(uuid, null, null, 0);
	}
	
	public Dependency(String uuid, int defaultPriority) {
		this(uuid, null, null, defaultPriority);
	}
	
	public Dependency(String uuid, String[] requires) {
		this(uuid, requires, null, 0);
	}
	
	public Dependency(String uuid, String[] requires, int defaultPriority) {
		this(uuid, requires, null, defaultPriority);
	}
	
	public Dependency(String uuid, String[] requires, String[] requiredBy) {
		this(uuid, requires, requiredBy, 0);
	}
	
	public Dependency(String uuid, String[] requires, String[] requiredBy, int defaultPriority) {
		this.uuid = uuid;
		this.requires = requires;
		this.requiredBy = requiredBy;
		this.defaultPriority = defaultPriority;
	}
	
	@Override
	public String uuid() {
		return uuid;
	}
	
	@Override
	public String[] requires() {
		return requires;
	}
	
	@Override
	public String[] requiredBy() {
		return requiredBy;
	}
	
	@Override
	public int defaultPriority() {
		return defaultPriority;
	}
	
	@Override
	public int hashCode() {
		return uuid.hashCode();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("uuid", uuid);
		tsh.add("requires", requires);
		tsh.add("requiredBy", requiredBy);
		tsh.add("defaultPriority", defaultPriority);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}