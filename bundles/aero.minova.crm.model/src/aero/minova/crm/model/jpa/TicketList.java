package aero.minova.crm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TicketList implements Serializable, List<Integer> {
	private static final long serialVersionUID = 202201021316L;

	private List<Integer> tickets = new ArrayList<>();

	public TicketList() {
	}

	public TicketList(String ticketList) {
		setTicketString(ticketList);
	}

	public void setTicketString(String ticketList) {
		tickets = new ArrayList<>();
		String ticketString[] = ticketList.split(" ");
		for (String string : ticketString) {
			try {
				tickets.add(Integer.parseInt(string));
			} catch (NumberFormatException | NullPointerException e) {
			}
		}
	}

	public String getTicketString() {
		StringBuilder sb = new StringBuilder();
		tickets.forEach(i -> sb.append(i + " "));
		return sb.toString();
	}
	
	public List<Integer> getList() {
		return tickets;
	}

	@Override
	public int size() {
		return tickets.size();
	}

	@Override
	public boolean isEmpty() {
		return tickets.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return tickets.contains(o);
	}

	@Override
	public Iterator<Integer> iterator() {
		return tickets.iterator();
	}

	@Override
	public Object[] toArray() {
		return tickets.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return tickets.toArray(a);
	}

	@Override
	public boolean add(Integer e) {
		return tickets.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return tickets.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return tickets.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		return tickets.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Integer> c) {
		return tickets.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return tickets.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return tickets.retainAll(c);
	}

	@Override
	public void clear() {
		tickets.clear();
	}

	@Override
	public Integer get(int index) {
		return tickets.get(index);
	}

	@Override
	public Integer set(int index, Integer element) {
		return tickets.set(index, element);
	}

	@Override
	public void add(int index, Integer element) {
		tickets.add(index, element);
	}

	@Override
	public Integer remove(int index) {
		return tickets.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return tickets.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return tickets.lastIndexOf(o);
	}

	@Override
	public ListIterator<Integer> listIterator() {
		return tickets.listIterator();
	}

	@Override
	public ListIterator<Integer> listIterator(int index) {
		return tickets.listIterator(index);
	}

	@Override
	public List<Integer> subList(int fromIndex, int toIndex) {
		return tickets.subList(fromIndex, toIndex);
	};
}
