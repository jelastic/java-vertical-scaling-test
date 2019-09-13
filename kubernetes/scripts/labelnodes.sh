NODES=$(kubectl get nodes | grep \<none\> | awk '{print $1}')
NODES=(${NODES// / })
LABELS=(java=openjdk java=adoptjdk java=zulujdk java=openj9 java=liberica java=zing)
for i in ${!NODES[*]}; do
  kubectl label nodes ${NODES[$i]} ${LABELS[$i]} --overwrite
done
