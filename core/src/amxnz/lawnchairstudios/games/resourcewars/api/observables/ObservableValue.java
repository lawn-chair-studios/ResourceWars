package amxnz.lawnchairstudios.games.resourcewars.api.observables;

import java.util.LinkedList;
import java.util.List;

public class ObservableValue<T> {
	private T value;

	public ObservableValue(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public interface Observer<T> {
		void espy(T oldValue, T newValue);
	}

	private final List<Observer<? super T>> observers = new LinkedList<Observer<? super T>>();

	public void addObserver(Observer<? super T> observer) {
		observers.add(observer);
	}

	public void setValue(T value) {
		for (Observer<? super T> o : observers)
			o.espy(this.value, value);
		this.value = value;
	}

}
