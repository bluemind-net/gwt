cd $(dirname $0)
apt install -qy zip unzip
export JAVA_HOME=/usr/local/lib/jdk17
export PATH=$JAVA_HOME/bin:$PATH
export GWT_MAVEN_REPO_ID=bm_releases
export GWT_MAVEN_REPO_URL=https://forge.bluemind.net/nexus/content/repositories/releases
export GWT_VERSION=2.11.0.bm2

maven/push-gwtproject.sh 

