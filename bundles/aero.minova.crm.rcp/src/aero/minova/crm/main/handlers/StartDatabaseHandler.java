
package aero.minova.crm.main.handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.e4.core.di.annotations.Execute;

public class StartDatabaseHandler {
	@Execute
	public void execute() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		conn.close();
	}
}