import axios from "axios";
import type {DailySchedule} from "@/models/DailySchedule";
import type {Ref, UnwrapRef} from "vue";


/**
 * Servicio para interactuar con el API de horarios diarios.
 * - `getDaily`: Obtiene la lista de horarios diarios.
 * - `createDaily`: Crea un nuevo horario diario.
 */


const API_URL = 'http://localhost:8080/api/days-schedules';

export default {
    async getDaily(): Promise<DailySchedule[]> {
        try {
            const response = await axios.get<DailySchedule[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createDaily(daily: {
        schedule: {
            id: number extends (Builtin | Ref | RefUnwrapBailTypes[keyof RefUnwrapBailTypes] | {
                [RawSymbol]?: true
            }) ? number : (number extends Map<infer K, infer V> ? (Map<K, UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof Map<any, any>>>) : (number extends WeakMap<infer K, infer V> ? (WeakMap<K, UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof WeakMap<any, any>>>) : (number extends Set<infer V> ? (Set<UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof Set<any>>>) : (number extends WeakSet<infer V> ? (WeakSet<UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof WeakSet<any>>>) : (number extends ReadonlyArray<any> ? { [K in keyof number]: UnwrapRefSimple<number[K]> } : (number extends (object & {
                [ShallowReactiveMarker]?: never
            }) ? { [P in keyof number]: P extends symbol ? number[P] : UnwrapRef<number[P]> } : number))))))
        };
        parkingFacility: {
            id: number extends (Builtin | Ref | RefUnwrapBailTypes[keyof RefUnwrapBailTypes] | {
                [RawSymbol]?: true
            }) ? number : (number extends Map<infer K, infer V> ? (Map<K, UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof Map<any, any>>>) : (number extends WeakMap<infer K, infer V> ? (WeakMap<K, UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof WeakMap<any, any>>>) : (number extends Set<infer V> ? (Set<UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof Set<any>>>) : (number extends WeakSet<infer V> ? (WeakSet<UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof WeakSet<any>>>) : (number extends ReadonlyArray<any> ? { [K in keyof number]: UnwrapRefSimple<number[K]> } : (number extends (object & {
                [ShallowReactiveMarker]?: never
            }) ? { [P in keyof number]: P extends symbol ? number[P] : UnwrapRef<number[P]> } : number))))))
        };
        dayWeek: {
            id: number extends (Builtin | Ref | RefUnwrapBailTypes[keyof RefUnwrapBailTypes] | {
                [RawSymbol]?: true
            }) ? number : (number extends Map<infer K, infer V> ? (Map<K, UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof Map<any, any>>>) : (number extends WeakMap<infer K, infer V> ? (WeakMap<K, UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof WeakMap<any, any>>>) : (number extends Set<infer V> ? (Set<UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof Set<any>>>) : (number extends WeakSet<infer V> ? (WeakSet<UnwrapRefSimple<V>> & UnwrapRef<Omit<number, keyof WeakSet<any>>>) : (number extends ReadonlyArray<any> ? { [K in keyof number]: UnwrapRefSimple<number[K]> } : (number extends (object & {
                [ShallowReactiveMarker]?: never
            }) ? { [P in keyof number]: P extends symbol ? number[P] : UnwrapRef<number[P]> } : number))))))
        }
    }): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, daily);
        return response.data;
    },

};
