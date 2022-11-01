import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table<N, V> {
	private ArrayList<N> name = new ArrayList<>();
	private ArrayList<V> value = new ArrayList<>();
	public String LOG = "";
	private int os = 0;
	private int ns = 0;

	public Table(ArrayList<N> n, ArrayList<V> v) {
		name = n;
		value = v;
		try {
			opt();
		} catch (IllegalStateException e) {
			LOG = e.getMessage();
		}
	}

	public Table() {
	}

	public Table(N[] a, V[] v) {
		name = toNA(a);
		value = toEA(v);
	}

	private ArrayList<N> toNA(N[] n) {
		ArrayList<N> r = new ArrayList<N>();
		for (int i = 0; i < n.length; i++) {
			r.add(n[i]);
		}
		return r;
	}

	private ArrayList<V> toEA(V[] e) {
		ArrayList<V> r = new ArrayList<>();
		for (int i = 0; i < e.length; i++) {
			r.add(e[i]);
		}
		return r;
	}

	private void opt() throws IllegalStateException {
		if (name.size() > value.size()) {
			os = name.size();
			throw new IllegalStateException("Name array size is larger than value array size");
		}
		if (name.size() < value.size()) {
			os = value.size();
			throw new IllegalStateException("Value array size is larger than Name array size");
		}
		if (os == ns) {
			throw new IllegalStateException("Failed to add new item to table");
		}
	}

	public Table<N, V> checkForErrors() {
		try {
			opt();
		} catch (IllegalStateException e) {
			if (e.getMessage() != "Failed to add new item to table") {
				LOG = e.getLocalizedMessage();
			} else {
				LOG = e.getLocalizedMessage() + "\n" + "Failed to add new item to table";
			}
		}
		return this;
	}

	public void add(N n, V v) {
		name.add(n);
		value.add(v);
		ns = max(name.size(), value.size());
		if (ns == os) {
			checkForErrors();
		} else {
			os = ns;
		}

	}

	private int max(int i, int i2) {
		return i > i2 ? i : i2;
	}

	public V getValueByName(N n) {
		try {
			opt();
		} catch (IllegalStateException e) {
			LOG = e.getMessage();
		}
		return value.get(name.indexOf(n));
	}

	public N getNameByValue(V v) {
		try {
			opt();
		} catch (IllegalStateException e) {
			LOG = e.getMessage();
		}
		return name.get(value.indexOf(v));
	}

	public boolean contains(N d) {
		return name.contains(d);
	}

	public int length() {
		return name.size() > value.size() ? name.size() : value.size();
	}

	public N getName(int index) {
		return name.get(index);
	}

	public V getValue(int index) {
		return value.get(index);
	}

	public String getLog() {
		return LOG;
	}
}
