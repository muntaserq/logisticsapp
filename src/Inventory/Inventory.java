package Inventory;
import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public interface Inventory {
	public void setDepleated(boolean value);

	public boolean isDepleated();
	
	public void setQuantity(Integer quantity) throws InvalidDataException;
	
	public Integer getQuantity();

	public String getItemId();

	public String getLocation();

	public String toString();
}
