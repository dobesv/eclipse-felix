
package felix;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class FelixStandaloneSetup extends FelixStandaloneSetupGenerated{

	public static void doSetup() {
		new FelixStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

