# This private feature corresponds to using a JSF-providing feature
# with just a container and no implementation (e.g. facesContainer-4.0)
-include= ~${workspace}/cnf/resources/bnd/feature.props
symbolicName=io.openliberty.facesProvider-4.0.0.Container
singleton=true
visibility=private
kind=noship
edition=full
WLP-Activation-Type: parallel
