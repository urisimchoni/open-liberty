-include= ~${workspace}/cnf/resources/bnd/feature.props
symbolicName=io.openliberty.batchSecurity-2.0
visibility=private
IBM-App-ForceRestart: install, \
 uninstall
IBM-Provision-Capability: osgi.identity; filter:="(&(type=osgi.subsystem.feature)(|(osgi.identity=io.openliberty.appSecurity-4.0)(osgi.identity=io.openliberty.appSecurity-5.0)(osgi.identity=io.openliberty.mpJwt-2.1)))", \
 osgi.identity; filter:="(&(type=osgi.subsystem.feature)(|(osgi.identity=io.openliberty.batch-2.0)(osgi.identity=io.openliberty.batch-2.1)))"
-features=com.ibm.wsspi.appserver.webBundleSecurity-1.0, \
  com.ibm.websphere.appserver.servlet-5.0; ibm.tolerates:="6.0"
-bundles=com.ibm.ws.jbatch.security.jakarta
IBM-Install-Policy: when-satisfied
kind=ga
edition=base
WLP-Activation-Type: parallel
