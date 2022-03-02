set -e

initialCwd=`pwd -P`
scriptDir=`dirname ${BASH_SOURCE[0]}`


OCP_USER=opentlc-mgr
OCP_PASSWORD=r3dh4t1!
oc login -u ${OCP_USER} -p ${OCP_PASSWORD}

# create route for registry
oc patch configs.imageregistry.operator.openshift.io/cluster -n openshift-image-registry --type merge -p '{"spec":{"defaultRoute":true}}'
REGISTRY_URL=$(oc get route default-route -n openshift-image-registry --template={{.spec.host}})
podman login -u $(oc whoami) -p $(oc whoami -t) ${REGISTRY_URL} --tls-verify=false

# install packages for building application
sudo yum install -y maven java-11-openjdk-devel
sudo alternatives --set java java-11-openjdk.x86_64

# Installing Podman
sudo yum install -y podman

export PROJECT_NAME=reactive-test
oc new-project ${PROJECT_NAME}


# set JAVA_HOME for Maven
export JAVA_HOME=/usr/lib/jvm/`rpm -q java-11-openjdk-devel | sed s/devel-//g`

cd ${scriptDir}/child-service-reactive
mvn clean package
podman build -f src/main/docker/Dockerfile.jvm -t ${PROJECT_NAME}/child-service-reactive-jvm .
cd ${initialCwd}
podman image tag ${PROJECT_NAME}/child-service-reactive-jvm:latest ${REGISTRY_URL}/${PROJECT_NAME}/child-service-reactive-jvm:latest
podman push ${REGISTRY_URL}/${PROJECT_NAME}/child-service-reactive-jvm:latest --tls-verify=false
sed -e "s/__PROJECT_NAME__/${PROJECT_NAME}/g" -e "s/__REGISTRY_URL__/${REGISTRY_URL}/g" ${scriptDir}/deploy_child_app.yaml > ${scriptDir}/child-service-reactive/deploy_child_app.yaml
oc apply -n ${PROJECT_NAME} -f ${scriptDir}/child-service-reactive/deploy_child_app.yaml

APPLICATIONS=(
"service-normal"
"service-reactive"
"service-reactive-normal-client"
)

for APP in "${APPLICATIONS[@]}" ; do
  cd ${scriptDir}/${APP}
  mvn clean package
  podman build -f src/main/docker/Dockerfile.jvm -t ${PROJECT_NAME}/${APP}-jvm .
  cd ${initialCwd}
  podman image tag ${PROJECT_NAME}/${APP}-jvm:latest ${REGISTRY_URL}/${PROJECT_NAME}/${APP}-jvm:latest
  podman push ${REGISTRY_URL}/${PROJECT_NAME}/${APP}-jvm:latest --tls-verify=false
  sed -e "s/__APP__/${APP}/g" -e "s/__PROJECT_NAME__/${PROJECT_NAME}/g" -e "s/__REGISTRY_URL__/${REGISTRY_URL}/g" ${scriptDir}/deploy_app.yaml > ${scriptDir}/${APP}/deploy_app.yaml
  oc apply -n ${PROJECT_NAME} -f ${scriptDir}/${APP}/deploy_app.yaml
done

echo "******"
echo "service-normal                : http://`oc -n reactive-test get route service-normal -o jsonpath='{.spec.host}'`/api"
echo "service-reactive              : http://`oc -n reactive-test get route service-reactive -o jsonpath='{.spec.host}'`/api"
echo "service-reactive-normal-client: http://`oc -n reactive-test get route service-reactive-normal-client -o jsonpath='{.spec.host}'`/api"
echo "child-service-reactive        : http://`oc -n reactive-test get route child-service-reactive -o jsonpath='{.spec.host}'`/delay"


cd ${initialCwd}

