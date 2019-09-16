NODES=$(kubectl get nodes | grep \<none\> | awk '{print $1}')
NODES=(${NODES// / })
LABELS=(gc=ZGC gc=Shenandoah gc=C4 gc=OpenJ9 gc=Epsilon gc=G1)
for i in ${!NODES[*]}; do
  kubectl label nodes ${NODES[$i]} ${LABELS[$i]} --overwrite
done
