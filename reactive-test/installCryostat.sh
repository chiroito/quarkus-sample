set -e

initialCwd=`pwd -P`
scriptDir=`dirname ${BASH_SOURCE[0]}`


RED_HAT_PAY_USER=opentlc-mgr
RED_HAT_PAY_PASSWORD=r3dh4t1!
oc login -u ${RED_HAT_PAY_USER} -p ${RED_HAT_PAY_PASSWORD}

export PROJECT_NAME=reactive-test

oc apply -n ${PROJECT_NAME} -f ${scriptDir}/deploy_cryostat.yaml

echo "Cryostat                      : http://`oc -n reactive-test get route cryostat-sample -o jsonpath='{.spec.host}'`"

cd ${initialCwd}
