package amxnz.lawnchairstudios.games.resourcewars.api.observables;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores a value that can be observed.
 *
 * @author Zeale
 *
 * @param <T> The type of value stored in this object.
 */
public class ObservableValue<T> {
	public interface Observer<T> {
		void espy(T oldValue, T newValue);
	}

	private T value;

	private final List<Observer<? super T>> observers = new LinkedList<>();

	public ObservableValue(T value) {
		this.value = value;
	}

	public void addObserver(Observer<? super T> observer) {
		observers.add(observer);
	}

	public T getValue() {
		return value;
	}

	public void removeObserver(Object observer) {
		observers.remove(observer);
	}

	public void setValue(T value) {
		for (Observer<? super T> o : observers)
			o.espy(this.value, value);
		this.value = value;
	}

}
