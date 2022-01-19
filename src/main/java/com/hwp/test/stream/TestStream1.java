package com.hwp.test.stream;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TestStream1 {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int a = 0; a < 12; a++) {
            map.put(a, (int) ((Math.random() * 8) + 1));
        }
        // ababa的key是map的值, value是map的元素下标.
        Map<Integer, List<Integer>> ababa = map.keySet().stream()
                .collect(Collectors.groupingBy(
                        junction -> map.get(junction)
                ));
//        System.out.println(ababa);
        TestStream1 test = new TestStream1();
        ababa.entrySet().stream().forEach(test::hwpPrint);


    }

    public static Integer getResult(Integer integer) {
        return integer;
    }

    public void hwpPrint(Object obj) {
        System.out.println("何伟平专属打印机:" + obj);
    }

//    private Map<Long, List<Merchant>> combineMerchantsWithChannelsAndMergeToAgencies(final List<EngagedMerchant> engagedMerchants) {
//        final Map<MerchantIds, List<ChannelRegistrationJunction>> registeredChannels = retrieveRegisteredChannels(
//                engagedMerchants.stream()
//                        .map(merchant -> MerchantIds.of(merchant.getEnterpriseId(), merchant.getStoreId()))
//                        .collect(Collectors.toList())
//        );
//        final Map<Long, List<ChannelApplication>> channelsInApplication = retrieveChannelsInApplication(
//                engagedMerchants.stream()
//                        .map(EngagedMerchant::getOwnerUserId)
//                        .collect(Collectors.toList())
//        );
//        return engagedMerchants.stream()
//                .collect(Collectors.groupingBy(EngagedMerchant::getAgencyId,
//                        Collector.of(
//                                ImmutableList::<Merchant>builder,
//
//                                (builder, merchant) -> builder.add(Merchant.from(
//                                        merchant,
//                                        registeredChannels.getOrDefault(MerchantIds.of(merchant.getEnterpriseId(), merchant.getStoreId()),
//                                                Collections.emptyList()),
//                                        channelsInApplication.getOrDefault(merchant.getOwnerUserId(), Collections.emptyList()))),
//
//                                (builder1, builder2) -> builder1.addAll(builder2.build()),
//
//                                ImmutableList.Builder::build
//                        )));
//    }

}
